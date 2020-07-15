package com.cloudxhoster.api.repository;

import com.cloudxhoster.api.Repository.CountryRepository;
import com.cloudxhoster.api.Repository.UserRepository;
import com.cloudxhoster.api.domain.CountryEntity;
import com.cloudxhoster.api.domain.UserEntity;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class UserRepositoryTest {

    @Inject private UserRepository repository;
    @Inject private CountryRepository countryRepository;

    @Test
    @DisplayName("Save a user in the database")
    @Transactional
    @Disabled
    public void save() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("john.wick@gmail.com");
        userEntity.setFirstName("John");
        userEntity.setLastName("Wick");
        userEntity.setCreationDate(new Date());
        userEntity.setVerified(false);
        CountryEntity countryEntity = this.countryRepository.find("from CountryEntity where phoneCode=33").firstResult();
        userEntity.setCountry(countryEntity);

        repository.persist(userEntity);
        assertNotNull(userEntity.getId());
        repository.delete(userEntity);
     }

}
