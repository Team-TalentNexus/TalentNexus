package org.thirtysix.talentnexus.service;

import org.thirtysix.talentnexus.dto.ResumeBasicDto;

public interface ResumeService {
    boolean createResume(ResumeBasicDto resumeBasicDto);
}
