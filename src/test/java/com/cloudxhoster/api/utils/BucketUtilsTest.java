package com.cloudxhoster.api.utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BucketUtilsTest {

    private static final Logger LOG = LoggerFactory.getLogger(BucketUtilsTest.class);

    @Test
    public void getBucketPolicyTest() {
        String policy = BucketUtils.getBucketPolicy("test");
        LOG.info(policy);
    }
}
