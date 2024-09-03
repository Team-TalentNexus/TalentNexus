package org.thirtysix.talentnexus.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thirtysix.talentnexus.mapper.JobPositionMapper;
import org.thirtysix.talentnexus.pojo.JobPosition;
import org.thirtysix.talentnexus.service.JobPositionService;

@Service
public class JobPositionServiceImpl implements JobPositionService {
    private static final Logger LOGGER = LogManager.getLogger(JobPositionServiceImpl.class);

    @Autowired
    JobPositionMapper jobPositionMapper;

    @Override
    public Integer getNumById(Integer id) {
        return jobPositionMapper.getNumById(id);
    }

    @Override
    public String getTitleById(Integer id) {
        return jobPositionMapper.getTitleById(id);
    }

    @Override
    public boolean addAddJobPosition(JobPosition jobPosition) {
        try {
            jobPositionMapper.addJobPosition(jobPosition);
        } catch (Exception e) {
            LOGGER.error("Error occurred during add job position: {}", e.getMessage());
            return false;
        }

        return true;
    }
}
