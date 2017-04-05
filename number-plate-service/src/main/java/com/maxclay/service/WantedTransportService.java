package com.maxclay.service;

import com.maxclay.model.DataSetVersion;
import com.maxclay.model.WantedTransport;

import java.util.List;

/**
 * @author Vlad Glinskiy
 */
public interface WantedTransportService {

    WantedTransport findOne(String id, DataSetVersion dataSetVersion);

    WantedTransport findOne(String id);

    void save(WantedTransport wantedTransport, DataSetVersion dataSetVersion);

    void save(WantedTransport wantedTransport);

    List<WantedTransport> findByNumberPlate(String numberPlate, DataSetVersion dataSetVersion);

    List<WantedTransport> findByNumberPlate(String numberPlate);
}
