package com.maxclay.service;

import com.fasterxml.jackson.databind.JsonNode;
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

    // lets return list, because we don't confident about number plate uniqueness...
    List<WantedTransport> findByNumberPlate(String numberPlate);

    List<WantedTransport> findByNumberPlate(String numberPlate, DataSetVersion dataSetVersion);

    void processRemoteData(JsonNode mvsData, DataSetVersion dataSetVersion);

    DataSetVersion findLatestDataSetVersion();
}
