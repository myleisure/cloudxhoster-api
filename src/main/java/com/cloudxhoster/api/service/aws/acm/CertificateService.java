package com.cloudxhoster.api.service.aws.acm;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acm.AcmClient;
import software.amazon.awssdk.services.acm.model.CertificateSummary;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CertificateService {

    Region DEFAULT_REGION = Region.US_EAST_1;

    List<CertificateSummary> listCertificates(Region region);

    void addCertificateDnsRecords(String domain, String certificateArn, Region region);

    String createCertificate(String domain, Region region);

    /**
     * Get AWS ACM client with default region
     *
     * @return AcmClient
     */
    default AcmClient getAcmClient() {
        return getAcmClient(DEFAULT_REGION);
    }

    /**
     * Get AWS ACM client with a specific region
     *
     * @return AcmClient
     */
    default AcmClient getAcmClient(@NotNull Region region) {
        if (region == null) throw new IllegalArgumentException("Region is mandatory");
        return AcmClient.builder().region(region).build();
    }
}
