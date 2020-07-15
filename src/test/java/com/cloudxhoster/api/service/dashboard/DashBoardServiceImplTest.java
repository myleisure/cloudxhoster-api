package com.cloudxhoster.api.service.dashboard;

import com.cloudxhoster.api.domain.HostingEntity;
import io.quarkus.test.junit.QuarkusTest;

import javax.inject.Inject;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@QuarkusTest
class DashBoardServiceImplTest {

    @Inject
    private DashBoardService dashBoardService;

    //@Test
    public void getHostings() {
        List<HostingEntity> hostingEntities = this.dashBoardService.getHostings(34L);
        assertFalse(hostingEntities.isEmpty());
    }
}