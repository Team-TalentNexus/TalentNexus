package org.thirtysix.talentnexus.service;

import org.thirtysix.talentnexus.pojo.Interview;

public interface InterviewService {

    Integer createInterview(Interview interview);
    boolean updateInterview(Interview interview);

    boolean deleteInterview(Integer id);

    Interview getInterviewById(Integer id);
}
