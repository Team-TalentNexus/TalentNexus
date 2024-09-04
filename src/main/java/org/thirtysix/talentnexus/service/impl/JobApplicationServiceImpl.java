package org.thirtysix.talentnexus.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thirtysix.talentnexus.mapper.JobApplicationMapper;
import org.thirtysix.talentnexus.pojo.JobApplication;
import org.thirtysix.talentnexus.service.JobApplicationService;

import java.util.List;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {
    private static final Logger LOGGER = LogManager.getLogger(JobApplicationServiceImpl.class);

    @Autowired
    JobApplicationMapper jobApplicationMapper;

    @Override
    public boolean submitApplication(JobApplication jobApplication) {
        try {
            jobApplicationMapper.submitApplication(jobApplication);
        } catch (Exception e) {
            LOGGER.error("Failed to submit an application: {}", e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<JobApplication> getApplicationByJobSeekerId(Integer id, Integer page, Integer size) {
        try {
            return jobApplicationMapper.getApplicationByJobSeekerId(id, size, (page - 1) * size);
        } catch (Exception e) {
            LOGGER.error("Error occurred while retrieving job applications for job seeker ID: {}", id, e);
            throw new RuntimeException("Failed to retrieve job applications for job seeker ID: " + id, e);
        }
    }

    @Override
    public Integer getActiveApplicationNumByJobSeekerId(Integer id) {
        return jobApplicationMapper.getActiveApplicationNumByJobSeekerId(id);
    }
}
