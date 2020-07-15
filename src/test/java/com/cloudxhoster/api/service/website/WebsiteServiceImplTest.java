package com.cloudxhoster.api.service.website;

import com.cloudxhoster.api.domain.UserEntity;
import com.cloudxhoster.api.exception.GenericException;
import com.cloudxhoster.api.service.user.UserService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class WebsiteServiceImplTest {

    @Inject private WebsiteService websiteService;
    @Inject private UserService userService;
    private String userEmail = "coucou@gmail.com";
    private String websiteName = "websiteTest";

    @BeforeEach
    public void init() {
        this.userService.deleteByEmail(userEmail);
        this.websiteService.delete(websiteName);
    }

    @Test
    void save() {
        this.userService.save(userEmail, "coucou", "lesgens", 63L);
        UserEntity userByEmail = this.userService.getUserByEmail(userEmail);
        assertDoesNotThrow(() -> this.websiteService.save(websiteName, userByEmail.getId()));
        assertThrows(GenericException.class, () -> this.websiteService.save(websiteName, userByEmail.getId()));
    }
}