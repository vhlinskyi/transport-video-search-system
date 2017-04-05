package com.maxclay.service.impl;

import com.maxclay.dao.DataSetVersionDao;
import com.maxclay.dao.WantedTransportDao;
import com.maxclay.model.DataSetVersion;
import com.maxclay.model.WantedTransport;
import com.maxclay.service.WantedTransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vlad Glinskiy
 */
@Service
public class WantedTransportServiceImpl implements WantedTransportService {

    private final WantedTransportDao wantedTransportDao;
    private final DataSetVersionDao dataSetVersionDao;

    @Autowired
    public WantedTransportServiceImpl(WantedTransportDao wantedTransportDao, DataSetVersionDao dataSetVersionDao) {
        this.wantedTransportDao = wantedTransportDao;
        this.dataSetVersionDao = dataSetVersionDao;
    }

    @Override
    public WantedTransport findOne(String id, DataSetVersion dataSetVersion) {

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Identifier can not be empty");
        }

        String dataSetId = dataSetVersion.getId();
        if (dataSetId == null || dataSetId.isEmpty() || !dataSetVersionDao.exists(dataSetId)) {
            throw new IllegalArgumentException("Data set version does not exist");
        }

        return wantedTransportDao.findOne(id, WantedTransport.GENERIC_COLLECTION_NAME + dataSetId);
    }

    @Override
    public WantedTransport findOne(String id) {
        DataSetVersion latest = dataSetVersionDao.findLatest();
        if (latest == null) {
            throw new IllegalStateException("There is no data set version");
        }

        return findOne(id, latest);
    }

    @Override
    public void save(WantedTransport wantedTransport, DataSetVersion dataSetVersion) {

        if (wantedTransport == null) {
            throw new IllegalArgumentException("Wanted transport can not be null");
        }

        String dataSetId = dataSetVersion.getId();
        if (dataSetId == null || dataSetId.isEmpty() || !dataSetVersionDao.exists(dataSetId)) {
            throw new IllegalArgumentException("Data set version does not exist");
        }

        wantedTransportDao.save(wantedTransport, WantedTransport.GENERIC_COLLECTION_NAME + dataSetId);
    }

    @Override
    public void save(WantedTransport wantedTransport) {
        DataSetVersion latest = dataSetVersionDao.findLatest();
        if (latest == null) {
            throw new IllegalStateException("There is no data set version");
        }

        save(wantedTransport, latest);
    }

    @Override
    public List<WantedTransport> findByNumberPlate(String numberPlate, DataSetVersion dataSetVersion) {

        if (numberPlate == null || numberPlate.isEmpty()) {
            throw new IllegalArgumentException("Number plate can not be empty");
        }

        String dataSetId = dataSetVersion.getId();
        if (dataSetId == null || dataSetId.isEmpty() || !dataSetVersionDao.exists(dataSetId)) {
            throw new IllegalArgumentException("Data set version does not exist");
        }

        return wantedTransportDao.findByNumberPlate(numberPlate, WantedTransport.GENERIC_COLLECTION_NAME + dataSetId);
    }

    @Override
    public List<WantedTransport> findByNumberPlate(String numberPlate) {
        DataSetVersion latest = dataSetVersionDao.findLatest();
        if (latest == null) {
            throw new IllegalStateException("There is no data set version");
        }

        return findByNumberPlate(numberPlate, latest);
    }
}
