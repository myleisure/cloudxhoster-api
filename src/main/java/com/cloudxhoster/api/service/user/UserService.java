package com.cloudxhoster.api.service.user;

import com.cloudxhoster.api.domain.UserEntity;
import com.cloudxhoster.api.exception.GenericException;

import java.util.List;
import java.util.Optional;

/**
 * User service. Used for all operations on service entity.
 */
public interface UserService {
    /**
     * Get all users.
     * @return
     */
    List<UserEntity> findAll();

    /**
     *
     * @param email
     * @param firstName
     * @param lastName
     * @param countryId
     * @return
     */
    void save(String email,
                 String firstName,
                 String lastName,
                 Long countryId) throws GenericException;

    /**
     * Get user by Id.
     * @param userId
     * @return
     * @throws GenericException
     */
    UserEntity getUser(Long userId) throws GenericException;

    /**
     * Delete user
     * @param email
     */
    void deleteByEmail(String email) throws GenericException;

    /**
     *
     * @param email
     * @return
     * @throws GenericException
     */
    UserEntity getUserByEmail(String email) throws GenericException;
}
