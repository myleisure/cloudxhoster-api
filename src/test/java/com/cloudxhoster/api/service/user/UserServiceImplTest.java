package com.cloudxhoster.api.service.user;

import com.cloudxhoster.api.exception.GenericException;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UserServiceImplTest {

    @Inject
    private UserService userService;

    private String email = "coucou@gmail.com";

    @BeforeEach
    public void init() {
        this.userService.deleteByEmail(email);
    }

    @AfterEach
    public void afterEach() {
        this.userService.deleteByEmail(email);
    }

    @Test
    public void saveTwoTimes() {
        assertDoesNotThrow(() -> this.userService.save(email, "coucou", "copains", 60L));
        assertThrows(GenericException.class, () -> this.userService.save(email, "coucou", "copains", 60L));
    }

    @Test
    public void deleteByEmail() {
        this.userService.save(email, "coucou", "copains", 60L);
        this.userService.deleteByEmail(email);
        assertThrows(GenericException.class, () -> this.userService.getUserByEmail(email));
    }
}