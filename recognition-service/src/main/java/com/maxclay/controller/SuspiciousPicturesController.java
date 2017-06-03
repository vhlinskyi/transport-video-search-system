package com.maxclay.controller;

import com.maxclay.config.FilesUploadProperties;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

/**
 * @author Vlad Glinskiy
 */
@Controller
public class SuspiciousPicturesController {

    private final Resource suspiciousImagesDir;

    @Autowired
    public SuspiciousPicturesController(FilesUploadProperties filesUploadProperties) {
        this.suspiciousImagesDir = filesUploadProperties.getSuspiciousPicturesPath();
    }

    @RequestMapping(value = "/suspicious-picture/{name:.+}")
    public void getUploadedPicture(@PathVariable String name, HttpServletResponse response) throws IOException {

        String suspiciousImagesDirPath = new FileSystemResource(suspiciousImagesDir.getFile()).getPath();
        String path = String.format("%s/%s", suspiciousImagesDirPath, name);

        Resource pic = new FileSystemResource(path);
        response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(pic.getFilename()));

        OutputStream out = response.getOutputStream();
        InputStream in = pic.getInputStream();
        IOUtils.copy(in, out);

        in.close();
        out.close();
    }
}
