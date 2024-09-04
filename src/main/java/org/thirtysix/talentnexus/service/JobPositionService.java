package org.thirtysix.talentnexus.service;

import org.thirtysix.talentnexus.pojo.JobPosition;

public interface JobPositionService {
    Integer getNumById(Integer id);
    String getTitleById(Integer id);
    boolean addAddJobPosition(JobPosition jobPosition);
    boolean deleteById(Integer id);
    Integer getCompanyIdById(Integer id);
}
