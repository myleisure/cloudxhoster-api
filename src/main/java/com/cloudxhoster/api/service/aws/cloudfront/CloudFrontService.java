package com.cloudxhoster.api.service.aws.cloudfront;

public interface CloudFrontService {

    String createDistribution(String domain, String websiteEndpoint, String certificateArn);

    void addCouldFrontDnsRecords(String domain, String cloudFrontDomain);
}
