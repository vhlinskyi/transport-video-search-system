package com.maxclay.converter;

import com.maxclay.dto.AccountDto;
import com.maxclay.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converter which performs conversion from {@link AccountDto} to
 * {@link UserDto}.
 *
 * @author Vlad Glinskiy
 */
@Component("accountDtoToUserDto")
public class AccountDtoToUserDto implements Converter<AccountDto, UserDto> {

    /**
     * Converts instances of {@link AccountDto} class into instances of
     * {@link UserDto} class.
     *
     * @param accountDto instance to be converted.
     * @return converted result instance of {@link UserDto} class.
     */
    @Override
    public UserDto convert(AccountDto accountDto) {

        if (accountDto == null) {
            throw new IllegalArgumentException("Accounts's data transfer object can not be null");
        }

        UserDto userDto = new UserDto();
        userDto.setUsername(accountDto.getEmail());
        userDto.setPassword(accountDto.getPassword());

        return userDto;
    }
}
