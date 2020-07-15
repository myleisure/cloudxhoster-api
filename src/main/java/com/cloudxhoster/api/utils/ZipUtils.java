package com.cloudxhoster.api.utils;

import com.cloudxhoster.api.enums.BusinessErrorCodeEnum;
import com.cloudxhoster.api.exception.GenericException;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ZipUtils.class);

    private ZipUtils() {
    }

    public static void extract(String originPath, String destination) throws GenericException {
        try {
            LOG.debug("[CloudxHosterAPI] Extracting zip file from {} to {}", originPath, destination);
            new ZipFile(originPath).extractAll(destination);
            LOG.debug("[CloudxHosterAPI] Extracted zip file from {} to {}", originPath, destination);
        } catch (ZipException e) {
            LOG.error("[CloudxHosterAPI] An error occured while extracting zip {} to {}: {}",
                    originPath, destination, e.getMessage());
            throw new GenericException("AN error occured while extracting archive "+ originPath, BusinessErrorCodeEnum.WEBSITE_EXTRACTION_ERROR);
        }
    }
}
