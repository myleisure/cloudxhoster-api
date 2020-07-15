package com.cloudxhoster.api.service.aws.route53;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.route53.Route53Client;
import software.amazon.awssdk.services.route53.model.*;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class DnsServiceImpl implements DnsService {

    @Override
    public void createHostedZone(String domain, Region region) {

        Route53Client route53Client = getRoute53Client(region);

        HostedZoneConfig hostedZoneConfig = HostedZoneConfig.builder()
                .privateZone(false)
                .comment(domain + " hosted zone")
                .build();

        CreateHostedZoneRequest hostedZoneRequest = CreateHostedZoneRequest.builder()
                .name(domain)
                .hostedZoneConfig(hostedZoneConfig)
                .callerReference(UUID.randomUUID().toString())
                .build();

        route53Client.createHostedZone(hostedZoneRequest);
    }

    @Override
    public void addRecords(List<ResourceRecordSet> resourceRecordSets, String domain, String comment) {

        Route53Client route53Client = getRoute53Client(Region.AWS_GLOBAL);

        Optional<HostedZone> hostedZone = route53Client.listHostedZonesByName(builder -> builder.dnsName(domain).build())
                .hostedZones().stream()
                .findAny();

        if (hostedZone.isPresent()) {

            List<Change> changes = resourceRecordSets.stream()
                    .map(resourceRecordSet -> Change.builder()
                            .action(ChangeAction.CREATE)
                            .resourceRecordSet(resourceRecordSet)
                            .build())
                    .collect(Collectors.toList());

            ChangeResourceRecordSetsRequest recordSetsRequest = ChangeResourceRecordSetsRequest.builder()
                    .hostedZoneId(hostedZone.get().id().substring(hostedZone.get().id().lastIndexOf("/") + 1))
                    .changeBatch(ChangeBatch.builder()
                            .changes(changes)
                            .comment(comment)
                            .build())
                    .build();

            route53Client.changeResourceRecordSets(recordSetsRequest);
        }
    }
}
