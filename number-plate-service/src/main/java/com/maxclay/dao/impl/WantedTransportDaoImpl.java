package com.maxclay.dao.impl;

import com.maxclay.dao.WantedTransportDao;
import com.maxclay.model.WantedTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @author Vlad Glinskiy
 */
public class WantedTransportDaoImpl implements WantedTransportDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public WantedTransport findOne(String id, String collection) {
        return mongoTemplate.findOne(Query.query(Criteria.where("id").is(id)), WantedTransport.class, collection);
    }

    @Override
    public List<WantedTransport> findByNumberPlate(String numberPlate, String collection) {
        return mongoTemplate.find(Query.query(Criteria.where("number_plate").is(numberPlate)),
                WantedTransport.class, collection);
    }


    @Override
    public void save(WantedTransport wantedTransport, String collection) {
        if (!mongoTemplate.collectionExists(collection)) {
            mongoTemplate.createCollection(collection);
        }
        mongoTemplate.save(wantedTransport, collection);
    }

}
