package org.thirtysix.talentnexus.service;

import org.thirtysix.talentnexus.dto.ResumeBasicDto;
import org.thirtysix.talentnexus.pojo.Resume;

public interface ResumeService {
    boolean createResume(ResumeBasicDto resumeBasicDto);

    Integer getJobSeekerIdById(Integer id);

    Resume getResumeByJobSeekerId(Integer jobSeekerId);

    Integer getIdByJobSeekerId(Integer id);

    boolean deleteResumeByJobSeekerId(Integer id);
}
