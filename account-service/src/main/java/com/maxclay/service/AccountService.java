package com.maxclay.service;

import com.maxclay.dto.AccountDto;
import com.maxclay.model.Account;

import java.util.List;

/**
 * Service, which provides methods that allow to manage {@link Account} instances.
 *
 * @author Vlad Glinskiy
 */
public interface AccountService {

    /**
     * Retrieves account by its identifier. Note that if there is no account with specified identifier,
     * {@link com.maxclay.exception.ResourceNotFoundException} unchecked exception will be thrown.
     *
     * @param id account's identifier.
     * @return account, whose identifier corresponds to the specified one.
     * @throws IllegalArgumentException                        if specified identifier is empty or <code>null</code>.
     * @throws com.maxclay.exception.ResourceNotFoundException if there is no account with specified identifier.
     */
    Account get(String id);

    /**
     * Retrieves list of all accounts.
     *
     * @return list of all accounts.
     */
    List<Account> getAll();

    /**
     * Retrieves account by its email address. Note that if there is no account with specified email address,
     * {@link com.maxclay.exception.ResourceNotFoundException} unchecked exception will be thrown.
     *
     * @param email account's email.
     * @return account, whose email corresponds to the specified one.
     * @throws IllegalArgumentException                        if specified email is empty or <code>null</code>.
     * @throws com.maxclay.exception.ResourceNotFoundException if there is no account with specified email.
     */
    Account getByEmail(String email);

    /**
     * Registers new account to the system according to the given user's data transfer object. Note, that data transfer
     * object must contain valid values and all required fields must be set, in other case
     * {@link com.maxclay.exception.ValidationException} unchecked exception will be thrown.
     *
     * @param accountDto user's data transfer object, which contains information required for the account registration.
     * @return registered account.
     * @throws IllegalArgumentException                  if the specified object is <code>null</code>.
     * @throws com.maxclay.exception.ValidationException if user's data transfer object contains invalid data.
     */
    Account register(AccountDto accountDto);

    /**
     * Saves specified account. If the given account exists, he will be updated, in other case new account will be saved.
     *
     * @param account account, which will be saved.
     * @return saved account.
     * @throws IllegalArgumentException if the specified object is <code>null</code>.
     */
    Account save(Account account);

    /**
     * Deletes specified account.
     *
     * @param account account, which will be deleted.
     * @throws IllegalArgumentException if the specified object is <code>null</code>.
     */
    void delete(Account account);

    /**
     * Deletes account according to his identifier.
     *
     * @param id account's identifier.
     * @throws IllegalArgumentException if specified identifier is empty or <code>null</code>.
     */
    void delete(String id);

    /**
     * Checks whether account with the specified identifier exists or not.
     *
     * @param id account's identifier.
     * @return 'true' if account with the specified identifier exists, 'false' otherwise.
     * @throws IllegalArgumentException if specified identifier is empty or <code>null</code>.
     */
    boolean exists(String id);

    /**
     * Checks whether specified email address available or not.
     *
     * @param email email address.
     * @return 'true' if the address is available, 'false' if some account with the specified address already exists.
     * @throws IllegalArgumentException if specified email is empty or <code>null</code>.
     */
    boolean emailAvailable(String email);
}
