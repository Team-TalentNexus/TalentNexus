package org.thirtysix.talentnexus.service;

import org.thirtysix.talentnexus.pojo.JobPosition;

public interface JobPositionService {
    Integer getNumById(Integer id);
    String getTitleById(Integer id);
    boolean addAddJobPosition(JobPosition jobPosition);
}
