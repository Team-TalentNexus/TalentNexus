package org.thirtysix.talentnexus.service;

import org.thirtysix.talentnexus.pojo.JobPosition;

import java.util.List;

public interface JobPositionService {
    Integer getNumById(Integer id);
    String getTitleById(Integer id);
    boolean addAddJobPosition(JobPosition jobPosition);
    boolean deleteById(Integer id);
    Integer getCompanyIdById(Integer id);
    List<JobPosition> getJobPositionsByCompanyId(Integer id, Integer page, Integer size);
    Integer getJobPositionsCountByCompanyId(Integer id);
    List<JobPosition> getAll(Integer page, Integer size);
}
