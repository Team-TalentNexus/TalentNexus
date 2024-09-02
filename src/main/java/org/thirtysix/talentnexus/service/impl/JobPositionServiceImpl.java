package org.thirtysix.talentnexus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thirtysix.talentnexus.mapper.JobPositionMapper;
import org.thirtysix.talentnexus.pojo.JobPosition;
import org.thirtysix.talentnexus.service.JobPositionService;

@Service
public class JobPositionServiceImpl implements JobPositionService {
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
}
