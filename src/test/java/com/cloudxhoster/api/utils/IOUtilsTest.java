package com.cloudxhoster.api.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Map;

public class IOUtilsTest {

    private static final Logger LOG = LoggerFactory.getLogger(IOUtilsTest.class);

    private Path workingDir;

    @BeforeEach
    public void init() {
        this.workingDir = Path.of("src/test/resources");
    }

    @Test
    public void getAllFilesTest() {
        String webSiteSrcDir = this.workingDir.resolve("website/timeless/").toString();
        Map<String, Path> allFiles = IOUtils.getAllFiles(webSiteSrcDir);
        allFiles.forEach((rel, abs) -> LOG.info("{} , {}", rel, abs.toString()));
    }
}