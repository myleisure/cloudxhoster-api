package com.cloudxhoster.api.utils;

import com.cloudxhoster.api.enums.BusinessErrorCodeEnum;
import com.cloudxhoster.api.exception.GenericException;
import org.apache.commons.io.FileUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

@ApplicationScoped
public class WebsiteUploadUtils {

    private static final Logger LOG = LoggerFactory.getLogger(WebsiteUploadUtils.class);

    private final String websiteUploadDirectory;

    @Inject
    public WebsiteUploadUtils(@ConfigProperty(name = "website.upload-directory") String websiteUploadDirectory) {
        this.websiteUploadDirectory = websiteUploadDirectory;
    }

    public String upload(String websiteName,  InputStream inputStream) {

        String uploadDestinationFilePath = websiteUploadDirectory + File.separator + websiteName + File.separator + "website.zip";
        try {
            Objects.requireNonNull(inputStream, "[CloudxHosterAPI] file stream must not be null");
            final var uploadedFile = new File(uploadDestinationFilePath);
            FileUtils.copyInputStreamToFile(inputStream, uploadedFile);
            return uploadDestinationFilePath;
        } catch (Exception e) {
            LOG.error("[CloudxHosterAPI] An error occured while uploading website archive");
            throw new GenericException("An error occured while uploading website archive : " + e.getMessage(), BusinessErrorCodeEnum.UPLOADING_ERROR);
        }
    }

    public String upload(String websiteName, String websiteArchiveUrl) {
        String uploadDestinationFilePath = websiteUploadDirectory + File.separator + websiteName + File.separator + "website.zip";
        try {
            final var uploadedFile = new File(uploadDestinationFilePath);
            FileUtils.copyURLToFile(new URL(websiteArchiveUrl), uploadedFile);
            return uploadDestinationFilePath;
        } catch (Exception e) {
            LOG.error("[CloudxHosterAPI] An error occured while uploading website archive");
            throw new GenericException("An error occured while uploading website archive : " + e.getMessage(), BusinessErrorCodeEnum.UPLOADING_ERROR);
        }
    }

}
