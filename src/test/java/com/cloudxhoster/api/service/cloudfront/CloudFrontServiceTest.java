package com.cloudxhoster.api.service.cloudfront;

import com.cloudxhoster.api.service.aws.cloudfront.CloudFrontService;
import com.cloudxhoster.api.service.aws.s3.BucketService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;

import javax.inject.Inject;

@QuarkusTest
public class CloudFrontServiceTest {

    private final Logger LOG = LoggerFactory.getLogger(CloudFrontServiceTest.class);

    @Inject
    private CloudFrontService cloudFrontService;

    @Inject
    private BucketService bucketService;

    //@Test
    public void createDistributionTest() {
        String domain = "cloudxhoster-website1.ml";
        String certificateArn = "arn:aws:acm:us-east-1:772621569605:certificate/5a3ce3cc-3325-40a1-957f-9e80a6107d95";
        //String websiteEndpoint = bucketService.getWebsiteEndpoint(domain, Region.US_EAST_1);
        //String cloudFrontDomain = cloudFrontService.createDistribution(domain, websiteEndpoint, certificateArn);
        String cloudFrontDomain = "d2jlur40x2ifqy.cloudfront.net";
        LOG.info(cloudFrontDomain);
        cloudFrontService.addCouldFrontDnsRecords(domain, cloudFrontDomain);
    }
}
