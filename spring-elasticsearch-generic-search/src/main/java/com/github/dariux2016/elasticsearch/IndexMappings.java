package com.github.dariux2016.elasticsearch;

public enum IndexMappings {
	
	CAP_INDEX("cap.settings", "cap.cap_type"),
	CUSTOMS_ENTRY_INDEX("customs_entry.settings", "customs_entry.customs_entry_type"),
	ITEM_EVENT_REASON_CODE_INDEX("item_event_reason_code.settings", "item_event_reason_code.item_event_reason_code_type"),
	POSTAL_OFFICE_INDEX("postal_office.settings", "postal_office.postal_office_type");
	
	private String settings;
	private String[] mappings;
	
	private IndexMappings(String settings, String... mappings) {
		this.settings = settings;
		this.mappings = mappings;
	}
	
	public String getSettings() {
		return this.settings;
	}
	
	public String[] getMappings() {
		return this.mappings;
	}
	
	public static IndexMappings getEnum(String name) {
		for(IndexMappings t : IndexMappings.values()) {
			if(t.name().equalsIgnoreCase(name)) {
				return t;
			}
		}
		return null;
	}
}
