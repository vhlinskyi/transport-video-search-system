package com.maxclay.dao;

import com.maxclay.model.WantedTransport;

import java.util.List;

/**
 * @author Vlad Glinskiy
 */
public interface WantedTransportOperations {

    List<WantedTransport> findByNumberPlate(String numberPlate);

    List<WantedTransport> findByNumberPlate(String numberPlate, boolean includeFound);

    List<WantedTransport> findNotFoundByMvs();
}
