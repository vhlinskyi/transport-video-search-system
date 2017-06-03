package com.maxclay.service;

import com.maxclay.model.SearchResult;

import java.util.List;

/**
 * @author Vlad Glinskiy
 */
public interface SearchService {
    List<SearchResult> recognizeAndSearch(byte[] image);
}
