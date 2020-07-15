package com.cloudxhoster.api.utils;

import com.cloudxhoster.api.exception.BucketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BucketUtils {

    private static final String S3_POLICY_FILE = "s3/policy.json";

    private static final Logger LOG = LoggerFactory.getLogger(BucketUtils.class);

    public static String getBucketPolicy(String bucket) {
        try {
            Path policyPath = Paths.get(ClassLoader.getSystemResource(S3_POLICY_FILE).toURI());
            String policyContents = Files.readString(policyPath, StandardCharsets.UTF_8)
                    .replace("${bucket}", bucket);
            if (!JsonUtils.isValidJSON(policyContents)) {
                LOG.error("Invalid S3 policy json conetents : {}", policyContents);
                throw new BucketException("Invliad s3 policy json conetents : " + policyContents);
            }
            return policyContents;
        } catch (IOException | URISyntaxException e) {
            LOG.error("Problem reading S3 policy file : {}", S3_POLICY_FILE);
            throw new BucketException("Problem reading s3 policy for bucket " + bucket);
        }
    }
}