package com.cloudxhoster.api.service.s3;

import com.cloudxhoster.api.model.Bucket;
import com.cloudxhoster.api.service.aws.s3.BucketService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@QuarkusTest
public class BucketServiceTest {

    private final Logger LOG = LoggerFactory.getLogger(BucketServiceTest.class);

    @Inject
    private BucketService bucketService;

    //@Test
    public void getBucketsTest() {
        List<Bucket> buckets = bucketService.getBuckets();
        LOG.info(buckets.stream().map(Bucket::getName).collect(Collectors.joining(",")));
        Assertions.assertFalse(buckets.isEmpty());
    }
}
