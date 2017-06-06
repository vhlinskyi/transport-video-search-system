package com.maxclay.controller;

import com.maxclay.config.ProfilePicturesUploadProperties;
import com.maxclay.dto.AccountDto;
import com.maxclay.model.Account;
import com.maxclay.service.AccountService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.security.Principal;

/**
 * Spring's {@link org.springframework.web.bind.annotation.RestController}, which handles requests related to the
 * {@link Account} resources.
 *
 * @author Vlad Glinskiy
 */
@RestController
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;
    private final Resource profilePicturesDir;
    private final Resource defaultProfilePicture;

    @Autowired
    public AccountController(AccountService accountService, ProfilePicturesUploadProperties picturesUploadProperties) {
        this.accountService = accountService;
        this.profilePicturesDir = picturesUploadProperties.getProfilePicturesDir();
        this.defaultProfilePicture = picturesUploadProperties.getDefaultProfilePicture();
    }

    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(path = "/{name}", method = RequestMethod.GET)
    public Account getAccountByUsername(@PathVariable String username) {
        // account's email corresponds to the unique username
        return accountService.getByEmail(username);
    }

    @RequestMapping(path = "/current", method = RequestMethod.GET)
    public Account getCurrentAccount(Principal principal) {
        return accountService.getByEmail(principal.getName());
    }


    @RequestMapping(path = "/picture", method = RequestMethod.GET)
    public void getDefaultUserPicture(HttpServletResponse response) throws IOException {
        writeImageToResponse(defaultProfilePicture, response);
    }

    @RequestMapping(path = "/picture/{name:.+}", method = RequestMethod.GET)
    public void getUsersPicture(@PathVariable String name, HttpServletResponse response) throws IOException {

        String profilePicturePath = new FileSystemResource(profilePicturesDir.getFile()).getPath();
        String path = String.format("%s/%s", profilePicturePath, name);

        Resource pic = new FileSystemResource(path);
        if (!pic.exists()) {
            pic = defaultProfilePicture;
        }

        writeImageToResponse(pic, response);
    }

    private void writeImageToResponse(Resource image, HttpServletResponse response) throws IOException {

        response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(image.getFilename()));
        OutputStream out = response.getOutputStream();
        InputStream in = image.getInputStream();
        IOUtils.copy(in, out);

        in.close();
        out.close();
    }

    @RequestMapping(path = "/current/update", method = RequestMethod.POST)
    public Account saveCurrentAccount(Principal principal, Account account, MultipartFile file) {
        return accountService.update(principal.getName(), account, file);
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public Account createNewAccount(@RequestBody AccountDto accountDto) {
        return accountService.register(accountDto);
    }

}
