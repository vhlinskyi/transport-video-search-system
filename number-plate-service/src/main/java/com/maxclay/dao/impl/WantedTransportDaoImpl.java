package com.maxclay.dao.impl;

import com.maxclay.dao.WantedTransportOperations;
import com.maxclay.model.WantedTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @author Vlad Glinskiy
 */
public class WantedTransportDaoImpl implements WantedTransportOperations {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<WantedTransport> findByNumberPlate(String numberPlate) {
        return findByNumberPlate(numberPlate, true);
    }

    @Override
    public List<WantedTransport> findByNumberPlate(String numberPlate, boolean excludeFoundByMVS) {
        Query query = new Query();
        Criteria criteria = Criteria.where("number_plate").is(numberPlate);

        if (excludeFoundByMVS) {
            criteria.andOperator(
                    Criteria.where("removed_at_version_id").exists(false)
            );
        }
        query.addCriteria(criteria);
        return mongoTemplate.find(query, WantedTransport.class);
    }

    @Override
    public List<WantedTransport> findNotFoundByMvs() {
        Query query = new Query().addCriteria(Criteria.where("removed_at_version_id").exists(false));
        return mongoTemplate.find(query, WantedTransport.class);
    }
}
