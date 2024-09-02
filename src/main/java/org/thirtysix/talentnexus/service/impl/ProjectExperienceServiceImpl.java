package org.thirtysix.talentnexus.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thirtysix.talentnexus.mapper.ProjectExperienceMapper;
import org.thirtysix.talentnexus.pojo.ProjectExperience;
import org.thirtysix.talentnexus.service.ProjectExperienceService;

@Service
public class ProjectExperienceServiceImpl implements ProjectExperienceService {
    @Autowired
    ProjectExperienceMapper projectExperienceMapper;

    private static final Logger LOGGER = LogManager.getLogger(ProjectExperienceServiceImpl.class);

    @Override
    public boolean add(ProjectExperience projectExperience) {
        try {
            projectExperienceMapper.add(projectExperience);
        } catch (Exception e) {
            LOGGER.error("Failed to add a project experience: {}", e.getMessage());
            return false;
        }

        return true;
    }
}
