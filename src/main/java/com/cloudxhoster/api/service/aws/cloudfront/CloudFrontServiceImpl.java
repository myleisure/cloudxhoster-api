package com.cloudxhoster.api.service.aws.cloudfront;

import com.cloudxhoster.api.service.aws.route53.DnsService;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;
import software.amazon.awssdk.services.cloudfront.model.*;
import software.amazon.awssdk.services.route53.model.AliasTarget;
import software.amazon.awssdk.services.route53.model.RRType;
import software.amazon.awssdk.services.route53.model.ResourceRecordSet;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CloudFrontServiceImpl implements CloudFrontService {

    private final DnsService dnsService;

    @Inject
    public CloudFrontServiceImpl(DnsService dnsService) {
        this.dnsService = dnsService;
    }

    @Override
    public String createDistribution(String domain, String websiteEndpoint, String certificateArn) {

        CloudFrontClient cloudFrontClient = CloudFrontClient.builder()
                .region(Region.AWS_GLOBAL)
                .build();

        Origin origin = Origin.builder()
                .domainName(websiteEndpoint)
                .id(domain)
                .customOriginConfig(CustomOriginConfig.builder()
                        .originProtocolPolicy(OriginProtocolPolicy.HTTP_ONLY)
                        .httpPort(80)
                        .httpsPort(443)
                        .build())
                .build();

        ViewerProtocolPolicy protocolPolicy = ViewerProtocolPolicy.REDIRECT_TO_HTTPS;

        Aliases aliases = Aliases.builder()
                .items(List.of(domain, "www." + domain))
                .quantity(2)
                .build();

        ForwardedValues forwardedValues = ForwardedValues.builder()
                .cookies(CookiePreference.builder().forward(ItemSelection.NONE).build())
                .queryString(false)
                .build();

        DefaultCacheBehavior cacheBehavior = DefaultCacheBehavior.builder()
                .defaultTTL(86400L)
                .minTTL(0L)
                .maxTTL(31536000L)
                .viewerProtocolPolicy(protocolPolicy)
                .allowedMethods(AllowedMethods.builder().items(Method.GET, Method.HEAD).quantity(2).build())
                .compress(false)
                .smoothStreaming(false)
                .targetOriginId(domain)
                .forwardedValues(forwardedValues)
                .trustedSigners(TrustedSigners.builder().enabled(false).quantity(0).build())
                .build();

        ViewerCertificate certificate = ViewerCertificate.builder()
                .sslSupportMethod(SSLSupportMethod.SNI_ONLY)
                .acmCertificateArn(certificateArn)
                .build();

        DistributionConfig config = DistributionConfig.builder()
                .comment(domain + " distribution")
                .callerReference(UUID.randomUUID().toString())
                .origins(builder -> builder.items(origin).quantity(1).build())
                .priceClass(PriceClass.PRICE_CLASS_ALL)
                .defaultCacheBehavior(cacheBehavior)
                .aliases(aliases)
                .viewerCertificate(certificate)
                .isIPV6Enabled(true)
                .enabled(true)
                .build();

        CreateDistributionRequest distributionRequest = CreateDistributionRequest.builder()
                .distributionConfig(config)
                .build();

        CreateDistributionResponse distribution = cloudFrontClient.createDistribution(distributionRequest);

        return distribution.distribution().domainName();
    }

    @Override
    public void addCouldFrontDnsRecords(String domain, String cloudFrontDomain) {
        ResourceRecordSet recordSet = ResourceRecordSet.builder()
                .name(domain)
                .type(RRType.A)
                .aliasTarget(AliasTarget.builder()
                        .dnsName(cloudFrontDomain)
                        .hostedZoneId(DnsService.AWS_CLOUDFRONT_HOSTED_ZONE_ID)
                        .evaluateTargetHealth(false)
                        .build())
                .build();
        dnsService.addRecords(List.of(recordSet), domain, "Add AWS Cloudfront DNS record");
    }
}