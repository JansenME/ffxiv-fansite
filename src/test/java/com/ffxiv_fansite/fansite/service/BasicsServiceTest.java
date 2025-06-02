package com.ffxiv_fansite.fansite.service;

import com.ffxiv_fansite.fansite.model.Job;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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

    private void assertOneJob(final Job job) {
        if (job.getBaseClass().equalsIgnoreCase("—")) {
            assertTrue(job.getBaseClassImageName().isEmpty());
        }

        if (!job.getBaseClass().equalsIgnoreCase("—")) {
            assertFalse(job.getBaseClassImageName().isEmpty());
        }
    }
}