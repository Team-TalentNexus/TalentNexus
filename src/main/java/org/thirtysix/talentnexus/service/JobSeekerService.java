package org.thirtysix.talentnexus.service;

import org.thirtysix.talentnexus.dto.JobSeekerLoginDto;
import org.thirtysix.talentnexus.pojo.JobSeeker;

public interface JobSeekerService {
    boolean login(JobSeekerLoginDto loginDto);
    String register(JobSeeker seeker);
}
