package com.cloudxhoster.api.service.website;

import com.cloudxhoster.api.Repository.WebsiteRepository;
import com.cloudxhoster.api.constants.persistence.UniqueConstraintNames;
import com.cloudxhoster.api.domain.UserEntity;
import com.cloudxhoster.api.domain.WebsiteEntity;
import com.cloudxhoster.api.enums.BusinessErrorCodeEnum;
import com.cloudxhoster.api.exception.GenericException;
import com.cloudxhoster.api.service.aws.s3.BucketService;
import com.cloudxhoster.api.service.user.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.Date;

@ApplicationScoped
public class WebsiteServiceImpl implements WebsiteService {

    private static final Logger LOG = LoggerFactory.getLogger(WebsiteServiceImpl.class);

    private final BucketService bucketService;
    private final WebsiteRepository websiteRepository;
    private final UserService userService;

    @Inject
    public WebsiteServiceImpl(BucketService bucketService, WebsiteRepository websiteRepository, UserService userService) {
        this.bucketService = bucketService;
        this.websiteRepository = websiteRepository;
        this.userService = userService;
    }

    @Override
    public void hostWebsiteInBucket(String domain, String region, String srcDir) {
        LOG.info("[CloudxHosterAPI] Hosting website's {} files from {} in {}", domain, srcDir, region);
        Region awsRegion = Region.of(region);
        bucketService.createBucket(domain, awsRegion);
        bucketService.setBucketPolicy(domain);
        bucketService.setBucketWebsiteHostingProperty(domain);
        bucketService.uploadFiles(domain, srcDir);
        bucketService.isBucketEndpointReachable(domain, awsRegion);
        LOG.info("[CloudxHosterAPI] Hosted website's {} files from {} in {}", domain, srcDir, region);
    }

    @Override
    @Transactional
    public WebsiteEntity save(String websiteName, Long userId) throws GenericException {
        try {
            final UserEntity userEntity = this.userService.getUser(userId);
            final var website = new WebsiteEntity(websiteName, userEntity, new Date());
            LOG.debug("[CloudxHosterAPI] Persist website : {}", website);
            this.websiteRepository.persist(website);
            LOG.debug("[CloudxHosterAPI] Persisted website : {}", website);
            return website;
        } catch (PersistenceException e) {
            return handlePersistingException(websiteName, e);
        }
    }

    @Override
    @Transactional
    public void delete(String websiteName) {
        this.websiteRepository.delete("from WebsiteEntity where name = ?1", websiteName);
    }

    private WebsiteEntity handlePersistingException(String websiteName, PersistenceException e) {
        if (e.getCause() instanceof ConstraintViolationException
                && UniqueConstraintNames.WEBSITE_ENTITY_NAME.equals(((ConstraintViolationException)e.getCause()).getConstraintName())) {
            LOG.error("[CloudxHosterAPI] A website named {} already exists in the database", websiteName);
            throw new GenericException("A website named "+ websiteName + " already exists", BusinessErrorCodeEnum.ITEM_ALREADY_EXISTS);
        }
        LOG.error("[CloudxHosterAPI] An error occured while persisting website with name {}", websiteName);
        throw new GenericException("An error occured while persisting website with name "+ websiteName , BusinessErrorCodeEnum.ITEM_SAVW_EXCEPTION);
    }
}
