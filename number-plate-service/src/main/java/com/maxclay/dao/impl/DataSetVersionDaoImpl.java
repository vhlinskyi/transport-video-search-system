package com.maxclay.dao.impl;

import com.maxclay.dao.DataSetVersionOperations;
import com.maxclay.model.DataSetVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author Vlad Glinskiy
 */
public class DataSetVersionDaoImpl implements DataSetVersionOperations {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public DataSetVersion findLatest() {
        Query query = new Query()
                .with(new Sort(Sort.Direction.DESC, "created"))
                .limit(1);

        return mongoTemplate.findOne(query, DataSetVersion.class);
    }
}
