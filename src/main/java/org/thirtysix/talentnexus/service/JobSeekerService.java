package org.thirtysix.talentnexus.service;

import org.thirtysix.talentnexus.dto.JobSeekerLoginDto;

public interface JobSeekerService {
    boolean login(JobSeekerLoginDto loginDto);
}
