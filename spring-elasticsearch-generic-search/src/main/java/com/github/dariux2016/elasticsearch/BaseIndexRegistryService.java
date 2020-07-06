package com.github.dariux2016.elasticsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public abstract class BaseIndexRegistryService<D extends BaseDocument, E extends BaseRegistryEntity> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseIndexRegistryService.class);
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ElasticsearchTemplate esTemplate;
	
	@Autowired
	protected BaseAssembler<M, D> documentAssembler;
	
	protected abstract String getIndexName();
	protected abstract String getIndexType();
	protected abstract Class<D> getDocumentClass();
	
	/**
	 * Provide the mapping for the names of the fields having multi-field indexing, like fields indexed as both text and keyword.<br>
	 * The keyword indexing is used for equal (exact match) searching and for sorting purposes.
	 * @return
	 */
	protected abstract Map<String, String> getFieldsMapping();
	
	public List<D> buildAllDocuments(int page) {
		List<M> assembleList = super.listPaged(page);
		return documentAssembler.disassembleList(assembleList);
	}
	
	public void createIndex() {
		String indexName = getIndexName();
		
		//clean index if existing
		if(esTemplate.indexExists(indexName)) {
			LOGGER.info("Deleting index {}", indexName);
			esTemplate.deleteIndex(indexName);
		}
		
		LOGGER.info("Creating index {}", indexName);
		
		final String INDEX_BASE_PATH = environment.getProperty(CommonConstants.ELASTICSEARCH_BASE_FOLDER_PROPERTY);
		final IndexMappings indexEnum = IndexMappings.getEnum(indexName);
		
		//create index with settings
		final String INDEX_SETTINGS_PROPERTY = CommonConstants.ELASTICSEARCH_BASE_PROPERTY + indexEnum.getSettings();
		final String INDEX_SETTINGS_RELATIVE = environment.getProperty(INDEX_SETTINGS_PROPERTY);
		final String INDEX_SETTINGS = INDEX_BASE_PATH + INDEX_SETTINGS_RELATIVE;
		LOGGER.info("Reading index settings from : {}", INDEX_SETTINGS);
		String settings = ReadJson.readJson(INDEX_SETTINGS);
		esTemplate.createIndex(indexName, settings);
		
		//put each mapping
		String[] mappings = indexEnum.getMappings();
		for (String mapping : mappings) {
			final String INDEX_MAPPING_RELATIVE = CommonConstants.ELASTICSEARCH_BASE_PROPERTY  + mapping;
			final String INDEX_MAPPING = INDEX_BASE_PATH + environment.getProperty(INDEX_MAPPING_RELATIVE);
			LOGGER.info("Reading index mapping from : {}", INDEX_MAPPING);
			String mappingJson = ReadJson.readJson(INDEX_MAPPING);
			esTemplate.putMapping(indexName, getIndexType(), mappingJson);
		}
	}
	
	/**
	 * Assuming an index is already defined in the server, re-populate the documents with all the records present in the database.
	 * If the index is not defined, create it before to use it.
	 */
	public void reindex() {
		ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.registerModule(new JavaTimeModule());
		jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
		String indexName = getIndexName();

		if (!esTemplate.indexExists(indexName)) {
			LOGGER.info("Index {} doesn't exist.", indexName);
			createIndex();
		}
		
		LOGGER.info("Re-indexing {} data", indexName);

		int page = 0;
		List<D> buildAll = this.buildAllDocuments(page);
		do {
			LOGGER.info("Re-indexing page {}", page);
			
			List<IndexQuery> queries = new ArrayList<>();
			for (D document : buildAll) {
				IndexQuery query = new IndexQuery();
				query.setId(document.getId());
				//query.setObject(document);
				try {
					query.setSource(jsonMapper.writeValueAsString(document));
				} catch (JsonProcessingException e) {
					LOGGER.error(e.getMessage(), e);
				}
				query.setIndexName(indexName);
				query.setType(getIndexType());
	
				queries.add(query);
			}
			esTemplate.bulkIndex(queries);
	
			esTemplate.refresh(indexName);
			
			buildAll = this.buildAllDocuments(++page);
		} while(buildAll != null && !buildAll.isEmpty());
	}
	
	/**
	 * Autocomplete user input, using indices. Search filters can be LIKE-,EQUAL-,PREFIX-style.<br>
	 * Provide paging and sorting functionalities to the search as results for non-paged fashion.
	 * @param request
	 * @return
	 */
	public SearchResponse<M> autocomplete(SearchRequest request) {
		
		List<SearchFilter> searchFilters = request.getSearchFilters();
		Map<String, String> fieldsMapping = getFieldsMapping();
		
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		
		if(searchFilters!=null) {
			
			for(SearchFilter sf : searchFilters) {
				Stream<String> fieldsStream = sf.getFields().stream();
				
				//input gets compared in lower-case fashion because text and keyword are indexed in lowercase (comparing in case-unsensitive way)
				String input = sf.getValue().toLowerCase();
				
				QueryBuilder queryBuilder = null;
				
				switch(sf.getSearchType()) {
					case LIKE:
						String[] likeFieldNames = fieldsStream.toArray(String[]::new);
						//assumption : like searchtype only over a field at time
						queryBuilder = QueryBuilders.queryStringQuery(input)
										.field(likeFieldNames[0])	
										.analyzeWildcard(true)
										//need the operator AND to continue autocompleting on the phrase-basis (take whitespace characters)
										.defaultOperator(Operator.AND);
						break;
					case EQUAL:
						//build an OR query : search the 'text' value in the 'fieldNames' specified
						String[] equalFieldNames = fieldsStream
														.map(s -> fieldsMapping.containsKey(s) ? fieldsMapping.get(s) : s)
														.toArray(String[]::new);
						
						MultiMatchQueryBuilder equal = QueryBuilders.multiMatchQuery(input, equalFieldNames);
						equal.type(MultiMatchQueryBuilder.Type.PHRASE);
						queryBuilder = equal;
						break;
					case PREFIX:
						String[] prefixFieldNames = fieldsStream.toArray(String[]::new);
						MultiMatchQueryBuilder prefix = QueryBuilders.multiMatchQuery(input, prefixFieldNames);
						prefix.type(MultiMatchQueryBuilder.Type.PHRASE_PREFIX);
						queryBuilder = prefix;
						break;
					default:
						break;
				}
				
				boolQueryBuilder.must(queryBuilder);
			}
		}
		
		List<Order> orders = new ArrayList<>();
		List<SearchSort> searchSorts = request.getSearchSorts();
		if(searchSorts != null && !searchSorts.isEmpty()) {
			
			for(int i=0; i<searchSorts.size(); i++) {
				SearchSort searchSort = searchSorts.get(i);
				String sortProperty = searchSort.getSort();
				if(fieldsMapping.containsKey(sortProperty)) {
					sortProperty = fieldsMapping.get(sortProperty);
				}
				
				Order order;
				switch(searchSort.getSortDirection()) {
					case ASC:
						order = Order.asc(sortProperty);
						break;
					case DESC:
						order = Order.desc(sortProperty);
						break;
					default:
						order = Order.asc(sortProperty);
						break;
				}
				orders.add(order);
			}
		}
		
		Pageable pageable;
		Integer page = request.getPage();
		if(page == null) {
			//get all the results
			pageable = Pageable.unpaged();
		} else {
			pageable = PageRequest.of(page-1, request.getPageSize(), Sort.by(orders));
		}
		
//		SourceFilter sourceFilter = new FetchSourceFilterBuilder().withExcludes("regione").build();
		
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(boolQueryBuilder)
				.withPageable(pageable)
//				.withSourceFilter(sourceFilter)
//				.withFilter(boolQueryBuilder)
				.build();
		
		AggregatedPage<D> pagedResult = esTemplate.queryForPage(searchQuery, getDocumentClass());
		
		if(pagedResult.hasContent()) {
			long totalElements = pagedResult.getTotalElements();
			int totalPages = pagedResult.getTotalPages();
			List<M> results = documentAssembler.assembleList(pagedResult.getContent());
			return new SearchResponse<>(totalPages,totalElements, results);
		}
		
		return new SearchResponse<>(0, 0, null);
	}
	
}