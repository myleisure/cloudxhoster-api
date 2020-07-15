package com.cloudxhoster.api.service.aws.route53;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.route53.Route53Client;
import software.amazon.awssdk.services.route53.model.ResourceRecordSet;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface DnsService {

    Region DEFAULT_REGION = Region.US_EAST_1;
    String AWS_CLOUDFRONT_HOSTED_ZONE_ID = "Z2FDTNDATAQYW2";

    void createHostedZone(String domain, Region region);

    void addRecords(List<ResourceRecordSet> resourceRecordSets, String domain, String comment);

    /**
     * Get AWS Route53 client with default region
     *
     * @return Route53Client
     */
    default Route53Client getRoute53Client() {
        return getRoute53Client(DEFAULT_REGION);
    }

    /**
     * Get AWS Route53 client with a specific region
     *
     * @return Route53Client
     */
    default Route53Client getRoute53Client(@NotNull Region region) {
        if (region == null) throw new IllegalArgumentException("Region is mandatory");
        return Route53Client.builder().region(region).build();
    }
}
