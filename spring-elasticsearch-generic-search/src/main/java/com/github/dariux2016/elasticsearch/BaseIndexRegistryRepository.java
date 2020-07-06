package com.github.dariux2016.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base interface for the repositories to search the Elasticsearch indexed documents
 */
@NoRepositoryBean
public interface BaseIndexRegistryRepository<T extends BaseDocument> extends ElasticsearchRepository<T, Long> {
}