package com.maxclay.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxclay.model.DataSetVersion;
import com.maxclay.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.Date;

/**
 * @author Vlad Glinskiy
 */
@Component
public class ScheduledDataFetchingComponent {

    // TODO move it to properties
    public static final String DATA_SET_URL = "http://data.gov.ua/view-dataset/dataset.json?dataset-id=589bb2f6-682c-4679-a81c-4070d92d8eef";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WantedTransportService wantedTransportService;

    // TODO use cron expression?
    @Scheduled(initialDelay = 1_000, fixedRate = 60 * 60_000)
    public void checkDataSetVersion() {

        RestTemplate restTemplate = new RestTemplate();
        JsonNode dataSetInfo = restTemplate.getForObject(DATA_SET_URL, JsonNode.class);
        Long remoteRevisionId = dataSetInfo.get("last_revision_id").asLong();
        Date changedDate = null;
        try {
            changedDate = DateUtils.valueOf(dataSetInfo.get("changed").asText(), "dd.MM.yyyy HH:mm");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        logger.info("Checking data set version. Last remote revision id: {}", remoteRevisionId);

        DataSetVersion latest = wantedTransportService.findLatestDataSetVersion();
        if (latest != null && remoteRevisionId.equals(latest.getRevisionId())) {
            logger.warn("Data already up to date. Revision id: {}", remoteRevisionId);
            return;
        }

        String wantedCarsDataURL = dataSetInfo.get("files").iterator().next().get("url").asText();
        logger.info("Fetching data from remote. URL: {}", wantedCarsDataURL);
        JsonNode wantedCarsData = restTemplate.getForObject(wantedCarsDataURL, JsonNode.class);
        logger.info("Data successfully fetched from remote. URL: {}", wantedCarsDataURL);
        wantedTransportService.processRemoteData(wantedCarsData, new DataSetVersion(remoteRevisionId, changedDate));

    }
}
