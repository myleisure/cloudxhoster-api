package com.cloudxhoster.api.mapper;

import com.cloudxhoster.api.domain.UserEntity;
import com.cloudxhoster.api.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named("userMapper")
public class UserMapper implements TransFormer<UserEntity, User> {

    @Override
    public User toSingle(UserEntity item) {

        if (item == null) return null;

        final var userDto = new User();
        userDto.setFirstName(item.getFirstName());
        userDto.setLastName(item.getLastName());
        userDto.setVerified(item.isVerified());
        userDto.setEmail(item.getEmail());
        userDto.setCreationDate(item.getCreationDate());
        return userDto;
    }
}
