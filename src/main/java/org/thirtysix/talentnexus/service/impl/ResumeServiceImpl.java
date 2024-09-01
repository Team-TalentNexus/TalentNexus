package org.thirtysix.talentnexus.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thirtysix.talentnexus.dto.ResumeBasicDto;
import org.thirtysix.talentnexus.mapper.ResumeMapper;
import org.thirtysix.talentnexus.service.ResumeService;

@Service
public class ResumeServiceImpl implements ResumeService {
    @Autowired
    private ResumeMapper resumeMapper;

    private static final Logger LOGGER = LogManager.getLogger(ResumeServiceImpl.class);

    @Override
    public boolean createResume(ResumeBasicDto resumeBasicDto) {
        try {
            resumeMapper.create(resumeBasicDto);
            return true;
        } catch (Exception e) {
            LOGGER.error("Failed to create resume: {}", e.getMessage());
            return false;
        }
    }

}
