package com.cloudxhoster.api.service.dashboard;

import com.cloudxhoster.api.Repository.HostingRepository;
import com.cloudxhoster.api.domain.HostingEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class DashBoardServiceImpl implements DashBoardService {

    private final HostingRepository hostingRepository;

    @Inject
    public DashBoardServiceImpl(HostingRepository hostingRepository) {
        this.hostingRepository = hostingRepository;
    }


    @Override
    public List<HostingEntity> getHostings(Long userId) {
        return hostingRepository.find("from HostingEntity where website.user.id=?1", userId).list();
    }
}
