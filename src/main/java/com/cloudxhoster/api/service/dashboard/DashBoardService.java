package com.cloudxhoster.api.service.dashboard;

import com.cloudxhoster.api.domain.HostingEntity;

import java.util.List;

public interface DashBoardService {
    /**
     * Get hostings for a particilar user
     * @param userId user ID
     * @return
     */
    List<HostingEntity> getHostings(Long userId);
}
