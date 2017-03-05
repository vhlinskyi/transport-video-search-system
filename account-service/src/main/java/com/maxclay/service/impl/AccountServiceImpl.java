package com.maxclay.service.impl;

import com.maxclay.client.AuthServiceClient;
import com.maxclay.dto.AccountDto;
import com.maxclay.dto.UserDto;
import com.maxclay.exception.ResourceNotFoundException;
import com.maxclay.exception.ValidationException;
import com.maxclay.model.Account;
import com.maxclay.repository.AccountRepository;
import com.maxclay.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

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

    @Autowired
    public AccountServiceImpl(@Qualifier("registrationValidator") Validator registrationValidator,
                              @Qualifier("conversionService") ConversionService conversionService,
                              AccountRepository accountRepository,
                              AuthServiceClient authServiceClient) {

        this.registrationValidator = registrationValidator;
        this.conversionService = conversionService;
        this.accountRepository = accountRepository;
        this.authServiceClient = authServiceClient;
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
