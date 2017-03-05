package com.maxclay.controller;

import com.maxclay.dto.AccountDto;
import com.maxclay.model.Account;
import com.maxclay.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Spring's {@link org.springframework.web.bind.annotation.RestController}, which handles requests related to the
 * {@link Account} resources.
 *
 * @author Vlad Glinskiy
 */
@RestController
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
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

    @RequestMapping(path = "/current", method = RequestMethod.PUT)
    public void saveCurrentAccount(Principal principal, @Valid @RequestBody Account account) {
        //TODO implement the updating logic
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public Account createNewAccount(@RequestBody AccountDto accountDto) {
        return accountService.register(accountDto);
    }

}
