package com.cloudxhoster.api.service.acm;

import com.cloudxhoster.api.service.aws.acm.CertificateService;
import com.cloudxhoster.api.service.aws.route53.DnsService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acm.model.CertificateSummary;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@QuarkusTest
public class CertificateServiceTest {

    private final Logger LOG = LoggerFactory.getLogger(CertificateServiceTest.class);

    @Inject
    private CertificateService certificateService;

    @Inject
    private DnsService dnsService;

    @Test
    public void listCertificatesTest() {
        Region region = Region.US_EAST_1;
        List<CertificateSummary> certificates = certificateService.listCertificates(region);
        certificates.forEach(cer -> LOG.info(cer.domainName()));
    }

    @Test
    public void getCertificateTest() {
        String domain = "cloudxhoster-website1.ml";
        //certificateService.getCertificate(domain);
    }

    //@Test
    public void createCertificateTest() throws InterruptedException {
        String domain = "cloudxhoster-website1.ml";
        Region region = Region.US_EAST_1;
        //dnsService.createHostedZone(domain, Region.AWS_GLOBAL);
        String certificateArn = certificateService.createCertificate(domain, region);
        List<CertificateSummary> certificates = certificateService.listCertificates(region);
        Optional<String> bucket = certificates.stream()
                .map(CertificateSummary::domainName)
                .filter(domain::equals)
                .findAny();
        Assertions.assertTrue(bucket.isPresent());
        TimeUnit.SECONDS.sleep(60);
        certificateService.addCertificateDnsRecords(domain, certificateArn, region);
    }
}
