package com.cloudxhoster.api.service.website;

import com.cloudxhoster.api.model.Bucket;
import com.cloudxhoster.api.service.aws.s3.BucketService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;

import javax.inject.Inject;
import java.util.Optional;

@QuarkusTest
public class BucketServiceTest {

    private final Logger LOG = LoggerFactory.getLogger(BucketServiceTest.class);

    @Inject
    private WebsiteService websiteService;

    @Inject
    private BucketService bucketService;

    //@Test
    public void hostWebsiteInBucket() {
        String domain = "cloudxhoster-website1.ml";
        String webSiteSrcDir = "C:\\Projects\\cloudxhoster-api\\src\\main\\resources\\websites\\timeless";
        websiteService.hostWebsiteInBucket(domain, Region.US_EAST_1.id(), webSiteSrcDir);
        Optional<String> bucket = bucketService.getBuckets()
                .stream()
                .map(Bucket::getName)
                .filter(domain::equals)
                .findAny();
        Assertions.assertTrue(bucket.isPresent());
    }
}
