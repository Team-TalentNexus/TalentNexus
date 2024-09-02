package org.thirtysix.talentnexus.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thirtysix.talentnexus.mapper.WorkExperienceMapper;
import org.thirtysix.talentnexus.pojo.WorkExperience;
import org.thirtysix.talentnexus.service.WorkExperienceService;

@Service
public class WorkExperienceServiceImpl implements WorkExperienceService {
    @Autowired
    WorkExperienceMapper workExperienceMapper;

    private static final Logger LOGGER = LogManager.getLogger(WorkExperienceServiceImpl.class);

    @Override
    public boolean add(WorkExperience workExperience) {
        try {
            workExperienceMapper.add(workExperience);
        } catch (Exception e) {
            LOGGER.error("Failed to add a work experience: {}", e.getMessage());
            return false;
        }

        return true;
    }
}
