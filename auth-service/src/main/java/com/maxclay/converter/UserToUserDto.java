package com.maxclay.converter;

import com.maxclay.dto.UserDto;
import com.maxclay.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converter which performs conversion from {@link com.maxclay.model.User} to
 * {@link com.maxclay.dto.UserDto}. Contains inner class which performs inverse conversion.
 *
 * @author Vlad Glinskiy
 */
@Component("userToUserDto")
public class UserToUserDto implements Converter<User, UserDto> {

    /**
     * Converts instances of {@link com.maxclay.model.User} class into instances of
     * {@link com.maxclay.dto.UserDto} class.
     *
     * @param user instance to be converted.
     * @return converted result instance of {@link com.maxclay.dto.UserDto} class.
     */
    @Override
    public UserDto convert(User user) {

        if (user == null) {
            throw new IllegalArgumentException("User can not be null");
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }

    /**
     * Converter which performs conversion from {@link com.maxclay.dto.UserDto} to
     * {@link com.maxclay.model.User}.
     */
    @Component("userToUserDtoInverse")
    public static class Inverse implements Converter<UserDto, User> {

        /**
         * Converts instances of {@link com.maxclay.dto.UserDto} class into instances of
         * {@link com.maxclay.model.User} class.
         *
         * @param userDto instance to be converted.
         * @return converted result instance of {@link com.maxclay.model.User}  class.
         */
        @Override
        public User convert(UserDto userDto) {

            if (userDto == null) {
                throw new IllegalArgumentException("User can not be null");
            }

            User user = new User();
            user.setId(userDto.getId());
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());

            return user;
        }
    }
}