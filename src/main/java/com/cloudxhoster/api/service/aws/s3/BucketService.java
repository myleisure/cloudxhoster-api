package com.cloudxhoster.api.service.aws.s3;

import com.cloudxhoster.api.model.Bucket;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface BucketService {

    Region DEFAULT_REGION = Region.US_EAST_1;
    String BUCKET_WEBSITE_ENDPOINT_TEMPLATE = "${bucket}.s3-website-${region}.amazonaws.com";

    /**
     * Create an AWS S3 bucket in a region
     *
     * @param bucket AWS S3 bucket's name
     * @param region AWS region
     */
    void createBucket(@NotNull String bucket, @NotNull Region region);

    /**
     * Set the AWS S3 bucket's access policy
     *
     * @param bucket AWS S3 bucket's name
     */
    void setBucketPolicy(@NotNull String bucket);

    /**
     * Set the AWS S3 bucket's static website hosting property
     *
     * @param bucket AWS S3 bucket's name
     */
    void setBucketWebsiteHostingProperty(@NotNull String bucket);

    /**
     * Upload all files to AWS S3 bucket from a directory and subdirectories
     *
     * @param bucket AWS S3 bucket's name
     * @param srcDir Files directory path
     */
    void uploadFiles(@NotNull String bucket, @NotNull String srcDir);

    /**
     * Empty AWS S3 bucket
     *
     * @param bucket bucket AWS S3 bucket's name
     */
    void emptyBucket(String bucket);

    /**
     * Delete AWS S3 bucket
     *
     * @param bucket AWS S3 bucket's name
     */
    void deleteBucket(String bucket);

    /**
     * List all buckets in AWS S3
     *
     * @return List<BucketDto>
     */
    List<Bucket> getBuckets();

    /**
     * Test if AWS S3 bucket's public static website's endpoint is reachable
     *
     * @param bucket AWS S3 bucket's name
     * @param region AWS region
     * @return boolean
     */
    boolean isBucketEndpointReachable(@NotNull String bucket, @NotNull Region region);

    /**
     * Get AWS S3 client with default region
     *
     * @return S3Client
     */
    default S3Client getS3Client() {
        return getS3Client(DEFAULT_REGION);
    }

    /**
     * Get AWS S3 client with a specific region
     *
     * @return S3Client
     */
    default S3Client getS3Client(@NotNull Region region) {
        if (region == null) throw new IllegalArgumentException("Region is mandatory");
        return S3Client.builder().region(region).build();
    }

    default String getWebsiteEndpoint(String bucket, Region region) {
        return BUCKET_WEBSITE_ENDPOINT_TEMPLATE
                .replace("${bucket}", bucket)
                .replace("${region}", region.id());
    }
}