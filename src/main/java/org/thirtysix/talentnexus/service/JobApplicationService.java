package org.thirtysix.talentnexus.service;

import org.thirtysix.talentnexus.pojo.JobApplication;

public interface JobApplicationService {
    boolean submitApplication(JobApplication jobApplication);
}
