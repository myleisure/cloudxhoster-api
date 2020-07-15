package com.cloudxhoster.api.utils;

import com.cloudxhoster.api.exception.GenericException;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class WebsiteUploadUtilsTest {

    private Path workingDir;

    @Inject
    private WebsiteUploadUtils websiteUploadUtils;

    @BeforeEach
    public void init() {
        this.workingDir = Path.of("src/test/resources");
    }

    @Test
    public void uploadFromTheWeb() throws GenericException {
        String destinationPath = websiteUploadUtils.upload("genericwebsite",
                "http://apache.crihan.fr/dist/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.zip");
        assertTrue(new File(destinationPath).exists());
    }

    @Test
    public void uploadFromInputStream() throws GenericException, IOException {
        Path file = this.workingDir.resolve("archive.zip");
        InputStream inputStream = Files.newInputStream(file);
        String destinationPath = websiteUploadUtils.upload("genericwebsite", inputStream);
        assertTrue(new File(destinationPath).exists());
    }

}