package com.github.dariux2016.elasticsearch;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SearchResponse<T> {

    public final int totalPages;
    public final long totalElements;
    public final List<T> results;

}
