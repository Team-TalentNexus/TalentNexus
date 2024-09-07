package org.thirtysix.talentnexus.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thirtysix.talentnexus.mapper.JobPositionMapper;
import org.thirtysix.talentnexus.pojo.JobPosition;
import org.thirtysix.talentnexus.service.JobPositionService;

import java.util.List;

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

    @Override
    public boolean deleteById(Integer id) {
        try {
            jobPositionMapper.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("Error occurred during delete job position:{}, {}", id, e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public Integer getCompanyIdById(Integer id) {
        try {
            return jobPositionMapper.getCompanyIdById(id);
        } catch (Exception e) {
            LOGGER.error("Error getCompanyIdById: id {}", id);
        }

        return null;
    }

    @Override
    public List<JobPosition> getJobPositionsByCompanyId(Integer id, Integer page, Integer size) {
        try {
            return jobPositionMapper.getJobPositionsByCompanyId(id, size, (page - 1) * size);
        } catch (Exception e) {
            LOGGER.error("Error getPositionsByCompanyId: id {}", id);
        }

        return null;
    }

    @Override
    public Integer getJobPositionsCountByCompanyId(Integer id) {
        return jobPositionMapper.getJobPositionsCountByCompanyId(id);
    }

    @Override
    public List<JobPosition> getAll(Integer page, Integer size) {
        return jobPositionMapper.getAll(size, (page - 1) * size);
    }

    @Override
    public Integer getAllCount() {
        return jobPositionMapper.getTotalCount();
    }
}
