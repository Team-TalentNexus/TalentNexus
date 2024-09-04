package org.thirtysix.talentnexus.service;

import org.thirtysix.talentnexus.pojo.JobApplication;

import java.util.List;

public interface JobApplicationService {
    boolean submitApplication(JobApplication jobApplication);

    List<JobApplication> getApplicationByJobSeekerId(Integer id, Integer page, Integer size);

    Integer getActiveApplicationNumByJobSeekerId(Integer id);
}
