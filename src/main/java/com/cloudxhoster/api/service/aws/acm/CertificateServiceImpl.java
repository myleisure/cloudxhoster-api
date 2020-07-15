package com.cloudxhoster.api.service.aws.acm;

import com.cloudxhoster.api.service.aws.route53.DnsService;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acm.AcmClient;
import software.amazon.awssdk.services.acm.model.*;
import software.amazon.awssdk.services.route53.model.RRType;
import software.amazon.awssdk.services.route53.model.ResourceRecordSet;
import software.amazon.awssdk.utils.CollectionUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class CertificateServiceImpl implements CertificateService {

    private final DnsService dnsService;

    @Inject
    public CertificateServiceImpl(DnsService dnsService) {
        this.dnsService = dnsService;
    }

    @Override
    public List<CertificateSummary> listCertificates(Region region) {
        AcmClient acm = getAcmClient();
        return acm.listCertificates().certificateSummaryList();
    }

    @Override
    public String createCertificate(String domain, Region region) {

        AcmClient acm = getAcmClient();

        RequestCertificateRequest certificateRequest = RequestCertificateRequest.builder()
                .domainName(domain)
                .tags(Tag.builder()
                        .key("Name")
                        .value(domain)
                        .build())
                .validationMethod(ValidationMethod.DNS)
                .subjectAlternativeNames("*." + domain)
                .build();

        return acm.requestCertificate(certificateRequest).certificateArn();
    }

    public void addCertificateDnsRecords(String domain, String certificateArn, Region region) {

        AcmClient acm = getAcmClient();

        Set<ResourceRecord> resourceRecords =
                acm.describeCertificate(builder -> builder.certificateArn(certificateArn))
                        .certificate().domainValidationOptions().stream()
                        .map(DomainValidation::resourceRecord)
                        .collect(Collectors.toSet());

        if (!CollectionUtils.isNullOrEmpty(resourceRecords)) {
            List<ResourceRecordSet> resourceRecordSets = resourceRecords.stream()
                    .filter(Objects::nonNull)
                    .map(resourceRecord ->
                            ResourceRecordSet.builder()
                                    .name(resourceRecord.name())
                                    .type(RRType.fromValue(resourceRecord.type().name()))
                                    .ttl(300L)
                                    .resourceRecords(software.amazon.awssdk.services.route53.model.ResourceRecord.builder()
                                            .value(resourceRecord.value())
                                            .build())
                                    .build())
                    .collect(Collectors.toList());

            if (!CollectionUtils.isNullOrEmpty(resourceRecords)) {
                System.out.println("resourceRecords is not empty");
                dnsService.addRecords(resourceRecordSets, domain, "Add AWS ACM certificate DNS records");
            }
        }
    }
}
