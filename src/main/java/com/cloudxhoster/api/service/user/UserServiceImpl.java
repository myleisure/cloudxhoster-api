package com.cloudxhoster.api.service.user;

import com.cloudxhoster.api.Repository.UserRepository;
import com.cloudxhoster.api.constants.persistence.UniqueConstraintNames;
import com.cloudxhoster.api.domain.CountryEntity;
import com.cloudxhoster.api.domain.UserEntity;
import com.cloudxhoster.api.enums.BusinessErrorCodeEnum;
import com.cloudxhoster.api.exception.GenericException;
import com.cloudxhoster.api.service.country.CountryService;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final CountryService countryService;

    @Inject
    public UserServiceImpl(UserRepository userRepository, CountryService countryService) {
        this.userRepository = userRepository;
        this.countryService = countryService;
    }

    @Override
    public List<UserEntity> findAll() {
        return this.userRepository.findAll().list();
    }

    @Override
    @Transactional
    public void save(String email,
                     String firstName,
                     String lastName,
                     Long countryId)
            throws GenericException {
        try {
            this.doSave(email, firstName, lastName, countryId);
        } catch (PersistenceException e) {
            this.catchUserPersistingException(email, e);
        }
    }

    private void catchUserPersistingException(String email, PersistenceException e) {
        boolean isEmailNotUnique = e.getCause() instanceof ConstraintViolationException
                && (((ConstraintViolationException) e.getCause()).getConstraintName().equals(UniqueConstraintNames.USER_EMAIL));
        if (isEmailNotUnique) {
            LOG.error("[CloudxHosterAPI] - User with email {} aoready exists in the database: {}", email, e.getMessage());
            throw new GenericException("A user with email : " + email + " already exist",  BusinessErrorCodeEnum.ITEM_ALREADY_EXISTS);
        }
        LOG.error("[CloudxHosterAPI] - An error occured while saving user with email : {}", email);
        throw new GenericException("An erro occured while saving user with email : " + email, BusinessErrorCodeEnum.ITEM_SAVW_EXCEPTION);
    }

    private void doSave(String email, String firstName, String lastName, Long countryId) {
        final CountryEntity countryEntity = this.countryService.getCountry(countryId)
                .orElseThrow(() -> new GenericException("Country not found", BusinessErrorCodeEnum.ITEM_NOT_FOUND));

        final var user = new UserEntity();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCountry(countryEntity);
        user.setCreationDate(new Date());

        LOG.debug("[CloudxHosterAPI] persisting user : {}", user);
        this.userRepository.persist(user);
        LOG.debug("[CloudxHosterAPI] persisted user : {}", user);
    }

    @Override
    public UserEntity getUser(Long userId) throws GenericException {
        return this.userRepository.findByIdOptional(userId)
                .orElseThrow(() -> {
                    LOG.error("[CloudxHosterAPI] - User with id : {} is not in the database", userId);
                    return new GenericException("User with id: " + userId + " is not found", BusinessErrorCodeEnum.ITEM_NOT_FOUND);
                });
    }

    @Override
    @Transactional
    public void deleteByEmail(String email) throws GenericException {
        LOG.debug("[CloudxHosterAPI] Deleting user with email : {}", email);
        this.userRepository.delete("from UserEntity where email = ?1", email);
        LOG.debug("[CloudxHosterAPI] Deleted user with email : {}", email);
    }

    @Override
    public UserEntity getUserByEmail(String email) throws GenericException {
        return this.userRepository.find("from UserEntity where email = ?1", email).firstResultOptional()
                .orElseThrow(() -> {
                    LOG.error("[CloudxHosterAPI] User with email : {} is not in the database", email);
                    return new GenericException("User with email " + email + " is not found", BusinessErrorCodeEnum.ITEM_NOT_FOUND);
                });

    }
}
