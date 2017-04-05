package com.maxclay.dao;

import com.maxclay.model.WantedTransport;

import java.util.List;

/**
 * @author Vlad Glinskiy
 */
public interface WantedTransportDao {

    WantedTransport findOne(String id, String collection);

    List<WantedTransport> findByNumberPlate(String numberPlate, String collection);

    void save(WantedTransport wantedTransport, String collection);
}
