package com.github.dariux2016.elasticsearch;

/**
 * Constants common to many applications
 */
public class CommonConstants {
	//CAMEL
	public static final String AUDIT_FLOW_PROPERTY="flow_name";
	public static final String AUDIT_DATA_PROPERTY="data";
	public static final String IPER_ITEM_LIST_PROPERTY="iper_item_list";
	public static final String IPER_PREDES_LIST_PROPERTY="iper_predes_list";
	public static final String ITEM_LIST_PROPERTY="item_list";
	public static final String PREDES_LIST_PROPERTY="predes_list";
	public static final String RECEPTACLE_PROPERTY="receptacle";
	public static final String DISPATCH_PROPERTY="dispatch";
	public static final String INPUT_PROPERTY = "inputProperty";
	public static final String OUTPUT_PROPERTY = "outputProperty";
	public static final String ROUTE_STEPS = "routeSteps";
	public static final String DUMP_PATH = "dumpPath";
	public static final String BARCODE_PROPERTY="barcode";
	public static final String PROCESS_PROPERTY="process";
	public static final String ITEM_PROPERTY="item";
	public static final String DISPATCH_LIST_ADVISE_VALIDATION="DISPATCH_LIST_ADVISE_VALIDATION";	
	public static final String ITEM_LIST_ADVISE_VALIDATION="ITEM_LIST_ADVISE_VALIDATION";
	
	public static final String MDC_CONTEXT_PARAM = "mdcContextParam";
	public static final String PRINT_HEADER = "requestedPrintList";
	public static final String MESSAGE_LIST_HEADER = "messageList";
	public static final String OK="OK";
	
	public static final String SUFFIX_CODE = ".code";
	public static final String SUFFIX_ENTITY = ".entity";
	public static final String SUFFIX_FIELD = ".field";
	public static final String SUFFIX_MESSAGE = ".message";
	
	public static final String ITALY_COUNTRY = "IT";
	public static final String SAN_MARINO_COUNTRY = "SM";
	public static final String VATICAN_COUNTRY = "VA";
	public static final String EUROPE_REGION = "Europe";
	
	//elasticsearch
	public static final String ELASTICSEARCH_BASE_PROPERTY= "elasticsearch.indices.";
	public static final String ELASTICSEARCH_BASE_FOLDER_PROPERTY = ELASTICSEARCH_BASE_PROPERTY + "base_folder";
	
	//COMMON ERROR MESSAGE
	public static final String GENERIC_INTERNAL_ERROR="application.system.internal.error";
	public static final String TOKEN_EXPIRED_ERROR="application.system.token.expired.error";
	public static final String MULTIPLE_TRANSACTIONS_ERROR="application.system.multiple.transactions.error";
	public static final String ROLE_BE = "ROLE_BE";
	public static final String BACKEND_TOKEN="BACKEND_TOKEN";
	
}
