package com.maxclay.dao;

import com.maxclay.model.DataSetVersion;

/**
 * @author Vlad Glinskiy
 */
public interface DataSetVersionOperations {
    DataSetVersion findLatest();
}
