package com.maxclay.service.impl;

import com.maxclay.client.NumberPlateSearchService;
import com.maxclay.model.*;
import com.maxclay.service.RecognitionService;
import com.maxclay.service.SearchService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vlad Glinskiy
 */
@Service
public class SearchServiceImpl implements SearchService {

    public static final String DEFAULT_IMAGE_EXTENSION = ".jpeg";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RecognitionService recognitionService;
    private final NumberPlateSearchService numberPlateSearchService;
    private final Resource imagesPath;

    @Autowired
    public SearchServiceImpl(RecognitionService recognitionService, NumberPlateSearchService numberPlateSearchService) {
        this.recognitionService = recognitionService;
        this.numberPlateSearchService = numberPlateSearchService;
        // TODO move path to properties
        this.imagesPath = new DefaultResourceLoader().getResource("file:./suspicious-matches");
    }

    @Override
    public List<SearchResult> recognizeAndSearch(byte[] image) {

        if (image == null || image.length == 0) {
            throw new IllegalArgumentException("Image data can not be empty");
        }

        RecognitionResult recognitionResult = recognitionService.recognize(image);
        if (recognitionResult.isEmpty()) {
            return Collections.emptyList();
        }

        List<PlateSearchRequest> plateSearchRequest = recognitionResult.getPlateResults().stream()
                .map(PlateRecognitionResult::getPlateNumber)
                .distinct()
                .map(PlateSearchRequest::new)
                .collect(Collectors.toList());

        List<WantedTransport> wantedTransports = numberPlateSearchService.search(plateSearchRequest);
        if (wantedTransports.isEmpty()) {
            return Collections.emptyList();
        }

        logger.info("Suspicious matches found: {}", wantedTransports);
        List<SearchResult> searchResult = wantedTransports.stream().map(SearchResult::new).collect(Collectors.toList());

        try {

            File tempFile = File.createTempFile("pic", DEFAULT_IMAGE_EXTENSION, imagesPath.getFile());
            try (InputStream in = new ByteArrayInputStream(image); OutputStream out = new FileOutputStream(tempFile)) {
                IOUtils.copy(in, out);
            }
            String imagePath = new FileSystemResource(tempFile).getPath();
            logger.info("Image of suspicious matches saved at: {}", imagePath);
            searchResult.forEach(res -> res.setImage(imagePath));
        } catch (Exception e) {
            logger.warn("Cant save image for suspicious matches", e);
        }

        return searchResult;
    }
}
