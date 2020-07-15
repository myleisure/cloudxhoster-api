package com.cloudxhoster.api.repository;

import com.cloudxhoster.api.Repository.HostingRepository;
import com.cloudxhoster.api.Repository.UserRepository;
import com.cloudxhoster.api.Repository.HostingRegionRepository;
import com.cloudxhoster.api.Repository.WebsiteRepository;
import com.cloudxhoster.api.Repository.CountryRepository;
import com.cloudxhoster.api.Repository.CloudProviderRepository;
import com.cloudxhoster.api.domain.*;
import com.cloudxhoster.api.enums.HostingStatusEnum;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.*;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class HostingRepositoryTest {

    @Inject
    private HostingRepository hostingRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private HostingRegionRepository hostingRegionRepository;

    @Inject
    private WebsiteRepository websiteRepository;

    @Inject
    private CountryRepository countryRepository;

    @Inject
    private CloudProviderRepository cloudProviderRepository;

    @Test
    @Transactional
    public void save() {
        String email = "james.cameron@gmail.com";
        final UserEntity userEntity = this.getPersistedUser(email);

        String websiteName = "example";
        final WebsiteEntity websiteEntity = this.getPersistedWebsite(userEntity, websiteName);

        HostingRegionEntity hostingRegionEntity = this.getPersistedHostingRegion();

        String domainName = "example.com";

        this.deleteIfHostingsAlreadyExist(websiteEntity, hostingRegionEntity, domainName);

        final HostingEntity hostingEntity = this.createHosting(websiteEntity, hostingRegionEntity, domainName);

        this.hostingRepository.persist(hostingEntity);

        assertTrue(this.hostingRepository.isPersistent(hostingEntity));

        this.hostingRepository.delete(hostingEntity);
        assertFalse(hostingRepository.isPersistent(hostingEntity));
    }


    private HostingEntity createHosting(WebsiteEntity websiteEntity, HostingRegionEntity hostingRegionEntity, String domainName) {
        final var hosting = new HostingEntity();
        hosting.setWebsite(websiteEntity);
        hosting.setHostingRegion(hostingRegionEntity);
        hosting.setDomainName(domainName);
        hosting.setStatus(HostingStatusEnum.DOWN);
        hosting.setCreationDate(new Date());
        hosting.setLastUpdate(new Date());
        return hosting;
    }

    private void deleteIfHostingsAlreadyExist(WebsiteEntity websiteEntity, HostingRegionEntity hostingRegionEntity, String domainName) {
        this.hostingRepository.find("from HostingEntity where (website.id=?1 and hostingRegion.id=?2) or domainName=?3",
                websiteEntity.getId(), hostingRegionEntity.getId(), domainName).list()
                .forEach(this.hostingRepository::delete);
    }

    private HostingRegionEntity getPersistedHostingRegion() {
        String codeRegion = "ÄSDD";
        return this.hostingRegionRepository.find("from HostingRegionEntity where id=?1 or code=?2", 2L, codeRegion)
                .firstResultOptional()
                .orElseGet(() -> {
                    final var h = new HostingRegionEntity();
                    final CloudProviderEntity cloudProviderEntity = this.getPersistedCloudProvider();
                    h.setCloudProvider(cloudProviderEntity);
                    h.setCreationDate(new Date());
                    h.setName("Asiaddsfsd");
                    h.setCode(codeRegion);
                    this.hostingRegionRepository.persist(h);
                    return h;
                });
    }

    private CloudProviderEntity getPersistedCloudProvider() {
        return cloudProviderRepository.find("from CloudProviderEntity where id=?1", 1L).firstResultOptional()
                .orElseGet(() -> {
                    final var cb = new CloudProviderEntity();
                    cb.setCode("AWS");
                    cb.setName("Amazon Web Services");
                    cb.setCreationDate(new Date());
                    cloudProviderRepository.persist(cb);
                    return cb;
                });
    }

    private WebsiteEntity getPersistedWebsite(UserEntity userEntity, String websiteName) {
        return this.websiteRepository.find("from WebsiteEntity where name = ?1", websiteName).firstResultOptional().orElseGet(() -> {
                final var w = new WebsiteEntity();
                w.setUser(userEntity);
                w.setName(websiteName);
                w.setCreationDate(new Date());
                this.websiteRepository.persist(w);
                return w;
            });
    }

    private UserEntity getPersistedUser(String email) {
        return this.userRepository.find("from UserEntity where email=?1", email).firstResultOptional().orElseGet(() -> {
                final UserEntity u = new UserEntity();
                u.setCountry(this.countryRepository.findById(38L));
                u.setCreationDate(new Date());
                u.setFirstName("james");
                u.setLastName("cameron");
                u.setEmail(email);
                this.userRepository.persist(u);
                return u;
            });
    }
}