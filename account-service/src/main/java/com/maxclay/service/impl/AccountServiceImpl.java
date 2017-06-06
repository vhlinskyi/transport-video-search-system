package com.maxclay.service.impl;

import com.maxclay.client.AuthServiceClient;
import com.maxclay.config.ProfilePicturesUploadProperties;
import com.maxclay.dto.AccountDto;
import com.maxclay.dto.UserDto;
import com.maxclay.exception.ResourceNotFoundException;
import com.maxclay.exception.ValidationException;
import com.maxclay.model.Account;
import com.maxclay.repository.AccountRepository;
import com.maxclay.service.AccountService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Vlad Glinskiy
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final Validator registrationValidator;
    private final ConversionService conversionService;
    private final AccountRepository accountRepository;
    private final AuthServiceClient authServiceClient;
    private final Resource profilePicturesDir;
    private final Resource defaultProfilePicture;

    @Autowired
    public AccountServiceImpl(@Qualifier("registrationValidator") Validator registrationValidator,
                              @Qualifier("conversionService") ConversionService conversionService,
                              AccountRepository accountRepository,
                              AuthServiceClient authServiceClient,
                              ProfilePicturesUploadProperties picturesUploadProperties) {

        this.registrationValidator = registrationValidator;
        this.conversionService = conversionService;
        this.accountRepository = accountRepository;
        this.authServiceClient = authServiceClient;
        this.profilePicturesDir = picturesUploadProperties.getProfilePicturesDir();
        this.defaultProfilePicture = picturesUploadProperties.getDefaultProfilePicture();
    }

    @Override
    public Account get(String id) {

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Account's identifier can not be empty");
        }

        Account account = accountRepository.findOne(id);
        if (account == null) {
            String message = String.format("Account with id = '%s' not found", id);
            throw new ResourceNotFoundException(message);
        }

        return account;
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account getByEmail(String email) {

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Account's email can not be empty");
        }

        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            String message = String.format("Account with email = '%s' not found", email);
            throw new ResourceNotFoundException(message);
        }

        return account;
    }

    @Override
    public Account register(AccountDto accountDto) {

        if (accountDto == null) {
            throw new IllegalArgumentException("AccountDto can not be null");
        }

        BindingResult errors = new BeanPropertyBindingResult(accountDto, "accountDto");
        registrationValidator.validate(accountDto, errors);
        if (errors.hasErrors()) {
            throw new ValidationException("Validation errors occurred while registering account.", errors);
        }

        if (!emailAvailable(accountDto.getEmail())) {
            throw new ValidationException("Validation errors occurred while registering user's account.",
                    "email is not available");
        }

        UserDto userDto = conversionService.convert(accountDto, UserDto.class);
        authServiceClient.registerUser(userDto);

        Account account = conversionService.convert(accountDto, Account.class);
        return accountRepository.save(account);
    }

    @Override
    public Account save(Account account) {

        if (account == null) {
            throw new IllegalArgumentException("Account can not be null");
        }

        return accountRepository.save(account);
    }

    @Override
    public Account update(String username, Account account, MultipartFile profilePicture) {

        if (account == null) {
            throw new IllegalArgumentException("Account can not be null");
        }
        // TODO validate
        Account existing = getByEmail(username);
        if (account.getFirstName() != null) {
            existing.setFirstName(account.getFirstName());
        }

        if (account.getLastName() != null) {
            existing.setLastName(account.getLastName());
        }

        if (account.getPhone() != null) {
            existing.setPhone(account.getPhone());
        }

        if (account.getSkypeName() != null) {
            existing.setSkypeName(account.getSkypeName());
        }

        if (account.getQuote() != null) {
            existing.setQuote(account.getQuote());
        }

        if (profilePicture == null || profilePicture.isEmpty()) {
            return save(existing);
        }

        if (!isImage(profilePicture)) {
            throw new ValidationException("Incorrect profile picture file. Please upload a picture.");
        }

        if (existing.getPicture() != null && !existing.getPicture().isEmpty()) {
            try {
                deletePictureFile(existing);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String picturePath = null;
        try {
            picturePath = savePictureFile(profilePicture);
        } catch (IOException e) {
            e.printStackTrace();
        }

        existing.setPicture(picturePath);
        return save(existing);
    }

    private String savePictureFile(MultipartFile profilePicture) throws IOException {
        
        String fileExtension = getFileExtension(profilePicture.getOriginalFilename());
        File tempFile = File.createTempFile("pic", fileExtension, profilePicturesDir.getFile());

        try (InputStream in = profilePicture.getInputStream(); OutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }

        return tempFile.getName();
    }

    private void deletePictureFile(Account account) throws IOException {

        Path path = Paths.get(account.getPicture());
        File f = path.toFile();
        if (f.exists()) {
            Files.delete(path);
        }
    }

    private boolean isImage(MultipartFile file) {
        return file.getContentType().startsWith("image");
    }

    private static String getFileExtension(String name) {
        return name.substring(name.lastIndexOf("."));
    }

    @Override
    public void delete(Account account) {

        if (account == null) {
            throw new IllegalArgumentException("Account can not be null");
        }

        accountRepository.delete(account);
    }

    @Override
    public void delete(String id) {

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Account's identifier can not be empty");
        }

        accountRepository.delete(id);
    }

    @Override
    public boolean exists(String id) {

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Account's identifier can not be empty");
        }

        return accountRepository.exists(id);
    }

    @Override
    public boolean emailAvailable(String email) {

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Account's email can not be empty");
        }

        return accountRepository.findByEmail(email) == null;
    }
}
