package com.maxclay.converter;

import com.maxclay.dto.AccountDto;
import com.maxclay.model.Account;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converter which performs conversion from {@link AccountDto} to
 * {@link Account}.
 *
 * @author Vlad Glinskiy
 */
@Component("accountDtoToAccount")
public class AccountDtoToAccount implements Converter<AccountDto, Account> {

    /**
     * Converts instances of {@link AccountDto} class into instances of
     * {@link Account} class.
     *
     * @param accountDto instance to be converted.
     * @return converted result instance of {@link Account} class.
     */
    @Override
    public Account convert(AccountDto accountDto) {

        if (accountDto == null) {
            throw new IllegalArgumentException("Accounts's data transfer object can not be null");
        }

        Account account = new Account();
        account.setId(accountDto.getId());
        account.setName(accountDto.getName());
        account.setEmail(accountDto.getEmail());
        account.setFirstName(accountDto.getFirstName());
        account.setLastName(accountDto.getLastName());
        account.setPhone(accountDto.getPhone());
        account.setSkypeName(accountDto.getSkypeName());
        account.setQuote(accountDto.getQuote());
        account.setPicture(accountDto.getPicture());

        return account;
    }
}