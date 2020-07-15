package com.cloudxhoster.api.service.hosting;

import com.cloudxhoster.api.Repository.HostingRepository;
import com.cloudxhoster.api.domain.HostingEntity;
import com.cloudxhoster.api.enums.HostingStatusEnum;
import com.cloudxhoster.api.domain.WebsiteEntity;
import com.cloudxhoster.api.model.request.HostingForm;
import com.cloudxhoster.api.exception.GenericException;
import com.cloudxhoster.api.model.request.HostingRequest;
import com.cloudxhoster.api.service.hosting_regoin.HostingRegionService;
import com.cloudxhoster.api.service.website.WebsiteService;
import com.cloudxhoster.api.utils.WebsiteUploadUtils;
import com.cloudxhoster.api.utils.ZipUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.File;
import java.util.Date;

@ApplicationScoped
public class HostingServiceImpl implements HostingService {

    private static final Logger LOG = LoggerFactory.getLogger(HostingServiceImpl.class);

    private final HostingRepository hostingRepository;
    private final WebsiteService websiteService;
    private final HostingRegionService hostingRegionService;
    private final WebsiteUploadUtils websiteUploadUtils;
    private final String websiteOutPutDirectory;

    @Inject
    public HostingServiceImpl(HostingRepository hostingRepository,
                              WebsiteService websiteService,
                              HostingRegionService hostingRegionService,
                              WebsiteUploadUtils websiteUploadUtils,
                              @ConfigProperty(name = "website.output-directory") String websiteOutPutDirectory) {
        this.hostingRepository = hostingRepository;
        this.websiteService = websiteService;
        this.hostingRegionService = hostingRegionService;
        this.websiteUploadUtils = websiteUploadUtils;
        this.websiteOutPutDirectory = websiteOutPutDirectory;
    }

    @Override
    @Transactional
    public void host(HostingForm requestBody)
            throws GenericException {
        final String websiteArchiveFilePath = this.websiteUploadUtils.upload(requestBody.getWebsiteName(),
                requestBody.getFile());
        this.persistHosting(requestBody.getDomainName(), requestBody.getWebsiteName(), requestBody.getRegion(),
                requestBody.getUserIdasLong());
        String srcDIr = this.extractArchive(requestBody.getWebsiteName(), websiteArchiveFilePath);
        this.websiteService.hostWebsiteInBucket(requestBody.getDomainName(), requestBody.getRegion(), srcDIr);
    }

    @Override
    @Transactional
    public void host(HostingRequest hostingRequest) throws GenericException {
        final String websiteArchiveFilePath = this.websiteUploadUtils.upload(hostingRequest.getWebsiteName(),
                hostingRequest.getArchiveUrl());
        this.persistHosting(hostingRequest.getDomainName(), hostingRequest.getWebsiteName(), hostingRequest.getRegion(),
                hostingRequest.getUserIdasLong());
        final String srcDIr = this.extractArchive(hostingRequest.getWebsiteName(), websiteArchiveFilePath);
        this.websiteService.hostWebsiteInBucket(hostingRequest.getDomainName(), hostingRequest.getRegion(), srcDIr);
    }

    private void persistHosting(String domainName, String websiteName, String region, Long userId) {
        final var hosting = this.createHosting(domainName, websiteName, region, userId);
        LOG.debug("[CloudxHosterAPI] Persisting hosting : {}", hosting);
        this.hostingRepository.persist(hosting);
        LOG.debug("[CloudxHosterAPI] Persisted hosting : {}", hosting);
    }

    private String extractArchive(String websiteName, String archiveFileUrl) throws GenericException {
        String outputDirectory = this.websiteOutPutDirectory + File.separator + websiteName;
        ZipUtils.extract(archiveFileUrl, outputDirectory);
        return outputDirectory + File.separator + "website";
    }

    private HostingEntity createHosting(String domainName, String websiteName, String region, Long userId)
            throws GenericException {

        final var hosting = new HostingEntity();
        hosting.setCreationDate(new Date());
        hosting.setLastUpdate(new Date());
        hosting.setStatus(HostingStatusEnum.DOWN);
        hosting.setDomainName(domainName);
        final WebsiteEntity websiteEntity = this.websiteService.save(websiteName, userId);
        hosting.setWebsite(websiteEntity);
        hosting.setHostingRegion(this.hostingRegionService.getHostingRegion(region));
        return hosting;
    }
}
