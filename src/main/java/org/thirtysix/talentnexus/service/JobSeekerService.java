package org.thirtysix.talentnexus.service;

import org.thirtysix.talentnexus.dto.JobSeekerLoginDto;
import org.thirtysix.talentnexus.pojo.JobSeeker;

public interface JobSeekerService {
    boolean login(JobSeekerLoginDto loginDto);
    String register(JobSeeker seeker);
    JobSeeker getByUsername(String username);
    Integer getIdByUsername(String username);
    JobSeeker getById(Integer id);
    String getFullNameById(Integer id);
}
