package com.maxclay.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxclay.dao.DataSetVersionDao;
import com.maxclay.dao.WantedTransportDao;
import com.maxclay.model.DataSetVersion;
import com.maxclay.model.WantedTransport;
import com.maxclay.service.WantedTransportService;
import com.maxclay.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vlad Glinskiy
 */
@Service
public class WantedTransportServiceImpl implements WantedTransportService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

        return wantedTransportDao.findOne(id);
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
        wantedTransport.setDataSetVersionId(dataSetId);
        wantedTransportDao.save(wantedTransport);
    }

    @Override
    public void save(WantedTransport wantedTransport) {

        String transportVersionId = wantedTransport.getDataSetVersionId();
        DataSetVersion dataSetVersion = (transportVersionId != null && !transportVersionId.isEmpty())
                ? dataSetVersionDao.findOne(transportVersionId)
                : dataSetVersionDao.findLatest();

        if (dataSetVersion == null) {
            throw new IllegalStateException("There is no data set version");
        }

        save(wantedTransport, dataSetVersion);
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

        return wantedTransportDao.findByNumberPlate(numberPlate);
    }

    @Override
    public List<WantedTransport> findByNumberPlate(String numberPlate) {
        DataSetVersion latest = dataSetVersionDao.findLatest();
        if (latest == null) {
            throw new IllegalStateException("There is no data set version");
        }

        return findByNumberPlate(numberPlate, latest);
    }

    @Override
    public void processRemoteData(JsonNode mvsData, DataSetVersion dataSetVersion) {
        logger.info("Processing new MVS data. Data set version: {}", dataSetVersion);
        dataSetVersionDao.save(dataSetVersion);
        logger.info("Saved data set version '{}'", dataSetVersion);

        Map<Long, WantedTransport> wantedTransportMap = wantedTransportDataToMap(mvsData);
        wantedTransportDao.findNotFoundByMvs().stream().forEach(wanted -> {

            // Wanted transport is absent in new MVS data set. Assume it is found.
            if (!wantedTransportMap.containsKey(wanted.getMvsId())) {
                wanted.setRemovedAtVersionId(dataSetVersion.getId());
                save(wanted);
            } else {
                wantedTransportMap.remove(wanted.getMvsId());
            }
        });

        // Currently in map left only new wanted transport, that should be saved to DB.
        wantedTransportMap.values().stream().forEach(this::save);
        logger.info("Successfully finished processing new MVS data. Data set version: {}", dataSetVersion);
    }

    @Override
    public DataSetVersion findLatestDataSetVersion() {
        return dataSetVersionDao.findLatest();
    }

    private Map<Long, WantedTransport> wantedTransportDataToMap(JsonNode wantedTransportData) {

        Map<Long, WantedTransport> wantedTransportMap = new HashMap<>();
        for (JsonNode transportDataJson : wantedTransportData) {
            WantedTransport wantedTransport = wantedTransportFromJsonData(transportDataJson);
            wantedTransportMap.put(wantedTransport.getMvsId(), wantedTransport);
        }

        return wantedTransportMap;
    }

    private WantedTransport wantedTransportFromJsonData(JsonNode wantedTransportData) {

        WantedTransport wantedTransport = new WantedTransport();
        wantedTransport.setChassisNumber(wantedTransportData.get("NSH").asText());
        wantedTransport.setBodyNumber(wantedTransportData.get("NKU").asText());
        wantedTransport.setNumberPlate(wantedTransportData.get("NOM").asText().replaceAll("\\s", ""));
        wantedTransport.setModel(wantedTransportData.get("MDL").asText());
        wantedTransport.setColor(wantedTransportData.get("COLOR").asText());

        try {
            Date registeredAsWanted = DateUtils.valueOf(wantedTransportData.get("dvv").asText(), "dd.MM.yyyy");
            wantedTransport.setRegisteredAsWanted(registeredAsWanted);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        wantedTransport.setMvsId(wantedTransportData.get("ID").asLong());
        wantedTransport.setDepartment(wantedTransportData.get("OVD").asText());

        return wantedTransport;
    }
}
