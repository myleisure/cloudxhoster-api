package com.cloudxhoster.api.utils;

import com.google.common.base.Functions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

public class IOUtils {

    private static final Logger LOG = LoggerFactory.getLogger(IOUtils.class);

    public static Map<String, Path> getAllFiles(String dirPath) {
        Path websiteSrcDir = Paths.get(dirPath);
        try {
            return Files.walk(websiteSrcDir)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toMap(p -> relativizePath(p, websiteSrcDir), Functions.identity()));
        } catch (IOException e) {
            LOG.error("[CloudxHosterAPI] Problem reading files from dir {} ", dirPath, e);
            throw new RuntimeException("Problem reading files from dir " + dirPath);
        }
    }

    private static String relativizePath(Path absPath, Path relPath) {
        return relPath.relativize(absPath)
                .toString()
                .replace("\\", "/");
    }
}
