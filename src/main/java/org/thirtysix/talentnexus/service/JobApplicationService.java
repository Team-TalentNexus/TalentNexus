package org.thirtysix.talentnexus.service;

import org.thirtysix.talentnexus.pojo.JobApplication;

import java.util.List;

public interface JobApplicationService {
    boolean submitApplication(JobApplication jobApplication);

    List<JobApplication> getApplicationByJobSeekerId(Integer id, Integer page, Integer size);

    Integer getActiveApplicationNumByJobSeekerId(Integer id);

    List<JobApplication> getApplicationsByCompanyIdAndJobPositionId(Integer companyId, Integer positionId,Integer page, Integer size);

    Integer getActiveApplicationNumByCompanyId(Integer id, Integer positionId);

    Integer getCountBySeekerIdAndPositionId(Integer jobSeekerId, Integer jobPositionId);
}
