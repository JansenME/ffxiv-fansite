package com.ffxiv_fansite.fansite.service;

import com.ffxiv_fansite.fansite.model.Aetheryte;
import com.ffxiv_fansite.fansite.model.Job;
import com.ffxiv_fansite.fansite.model.Region;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class BasicsServiceTest {
    @InjectMocks BasicsService basicsService;

    @Test
    void testGetExpansionsHappyFlow() {
        assertEquals(6, basicsService.getExpansions().size());
    }

    @Test
    void testGetMajorCitiesHappyFlow() {
        assertEquals(8, basicsService.getMajorCities().size());
    }

    @Test
    void testGetMinorCitiesHappyFlow() {
        assertEquals(6, basicsService.getMinorCities().size());
    }

    @Test
    void testGetJobsHappyFlow() {
        assertEquals(22, basicsService.getJobs().size());
    }

    @Test
    void testJobsListNoBaseClassIsNoImage() {
        List<Job> jobs = basicsService.getJobs();

        jobs.forEach(this::assertOneJob);
    }

    @Test
    void testGetCraftersHappyFlow() {
        assertEquals(8, basicsService.getCrafters().size());
    }

    @Test
    void testGetGatherersHappyFlow() {
        assertEquals(3, basicsService.getGatherers().size());
    }

    @Test
    void testGetRegionsHappyFlow() {
        assertEquals(19, basicsService.getRegions().size());
    }

    @Test
    void testGetZonesHappyFlow() {
        assertEquals(76, basicsService.getZones().size());
    }

    @Test
    void testGetAetherytesByRegionHappyFlow() {
        Map<Region, List<Aetheryte>> result = basicsService.getAetherytesByRegion();

        assertEquals(18, result.size());
        assertEquals(10, result.get(Region.LA_NOSCEA).size());
        assertEquals(6, result.get(Region.THE_BLACK_SHROUD).size());
        assertEquals(9, result.get(Region.THANALAN).size());
        assertEquals(3, result.get(Region.COERTHAS).size());
        assertEquals(1, result.get(Region.MOR_DHONA).size());
        assertEquals(3, result.get(Region.ABALATHIAS_SPINE).size());
        assertEquals(5, result.get(Region.DRAVANIA).size());
        assertEquals(7, result.get(Region.GYR_ABANIA).size());
        assertEquals(1, result.get(Region.HINGASHI).size());
        assertEquals(8, result.get(Region.OTHARD).size());
        assertEquals(17, result.get(Region.NORVRANDT).size());
        assertEquals(4, result.get(Region.THE_NORTHERN_EMPTY).size());
        assertEquals(6, result.get(Region.ILSABARD).size());
        assertEquals(5, result.get(Region.THE_SEA_OF_STARS).size());
        assertEquals(3, result.get(Region.THE_WORLD_UNSUNDERED).size());
        assertEquals(9, result.get(Region.YOK_TURAL).size());
        assertEquals(7, result.get(Region.XAK_TURAL).size());
        assertEquals(3, result.get(Region.UNLOST_WORLD).size());
    }

    private void assertOneJob(final Job job) {
        if (job.getBaseClass().equalsIgnoreCase("—")) {
            assertTrue(job.getBaseClassImageName().isEmpty());
        }

        if (!job.getBaseClass().equalsIgnoreCase("—")) {
            assertFalse(job.getBaseClassImageName().isEmpty());
        }
    }
}