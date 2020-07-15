package com.cloudxhoster.api.service.aws.s3;


import com.cloudxhoster.api.model.Bucket;
import com.cloudxhoster.api.utils.BucketUtils;
import com.cloudxhoster.api.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import javax.enterprise.context.ApplicationScoped;
import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BucketServiceImpl implements BucketService {

    private static final Logger LOG = LoggerFactory.getLogger(BucketServiceImpl.class);

    @Override
    public void createBucket(String bucket, Region region) {
        LOG.info("[CloudxHosterAPI] Creating bucket {} in {}", bucket, region);
        S3Client s3 = getS3Client(region);
        CreateBucketRequest createBucketRequest = CreateBucketRequest
                .builder()
                .bucket(bucket)
                .createBucketConfiguration(CreateBucketConfiguration.builder()
                        //.locationConstraint(region.id())
                        .build())
                .build();
        s3.createBucket(createBucketRequest);
        LOG.info("[CloudxHosterAPI] Created bucket {} in {}", bucket, region);
    }

    @Override
    public void setBucketPolicy(String bucket) {
        LOG.info("[CloudxHosterAPI] Setting bucket's {} policy", bucket);
        S3Client s3 = getS3Client();
        PutBucketPolicyRequest policyReq = PutBucketPolicyRequest.builder()
                .bucket(bucket)
                .policy(BucketUtils.getBucketPolicy(bucket))
                .build();
        s3.putBucketPolicy(policyReq);
        LOG.info("[CloudxHosterAPI] Sat bucket's {} policy", bucket);
    }

    @Override
    public void setBucketWebsiteHostingProperty(String bucket) {
        LOG.info("[CloudxHosterAPI] Setting bucket's {} static website hosting property", bucket);
        S3Client s3 = getS3Client();
        WebsiteConfiguration configuration = WebsiteConfiguration.builder()
                .indexDocument(IndexDocument.builder().suffix("index.html").build())
                .errorDocument(ErrorDocument.builder().key("index.html").build())
                .build();
        PutBucketWebsiteRequest websiteRequest = PutBucketWebsiteRequest.builder()
                .bucket(bucket)
                .websiteConfiguration(configuration)
                .build();
        s3.putBucketWebsite(websiteRequest);
        LOG.info("[CloudxHosterAPI] Sat bucket's {} static website hosting property", bucket);
    }

    @Override
    public void uploadFiles(String bucket, String srcDir) {
        LOG.info("[CloudxHosterAPI] Uploading files to bucket {} from {}", bucket, srcDir);
        S3Client s3 = getS3Client();
        IOUtils.getAllFiles(srcDir).forEach((relPath, absPath) -> s3.putObject(builder ->
                        builder.bucket(bucket)
                                .key(relPath)
                                .build(),
                absPath));
        LOG.info("[CloudxHosterAPI] Uploaded website files to bucket {} from {}", bucket, srcDir);
    }

    @Override
    public void emptyBucket(String bucket) {
        LOG.info("[CloudxHosterAPI] Emptying bucket {}", bucket);
        S3Client s3 = getS3Client();
        s3.deleteObject(builder -> builder.bucket(bucket).build());
        LOG.info("[CloudxHosterAPI] emptied bucket {}", bucket);
    }

    @Override
    public void deleteBucket(String bucket) {
        LOG.info("[CloudxHosterAPI] Deleting bucket {}", bucket);
        S3Client s3 = getS3Client();
        this.emptyBucket(bucket);
        s3.deleteBucket(builder -> builder.bucket(bucket).build());
        LOG.info("[CloudxHosterAPI] Deleted bucket {}", bucket);
    }

    @Override
    public List<Bucket> getBuckets() {
        LOG.info("[CloudxHosterAPI] Listing buckets");
        S3Client s3 = getS3Client();
        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3.listBuckets(listBucketsRequest);
        List<Bucket> buckets = listBucketsResponse.buckets()
                .stream()
                .map(bucket -> new Bucket(bucket.name(), bucket.creationDate()))
                .collect(Collectors.toList());
        LOG.info("[CloudxHosterAPI] Listed buckets");
        return buckets;
    }

    @Override
    public boolean isBucketEndpointReachable(String bucket, Region region) {
        LOG.info("[CloudxHosterAPI] Testing if bucket's {} endpoint is reachable", bucket);
        String endpoint = getWebsiteEndpoint(bucket, region);
        boolean reachable;
        try {
            reachable = InetAddress.getByName(endpoint).isReachable(10000);
        } catch (Exception e) {
            LOG.error("[CloudxHosterAPI] Problem testing if bucket's {} endpoint {} is reachable ",
                    bucket, endpoint, e);
            return false;
        }
        LOG.warn("[CloudxHosterAPI] bucket's {} endpoint {} is {} reachable", bucket, endpoint, reachable ? "" : "not");
        return reachable;
    }
}