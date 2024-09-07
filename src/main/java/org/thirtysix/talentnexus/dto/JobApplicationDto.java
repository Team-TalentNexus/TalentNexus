package org.thirtysix.talentnexus.dto;

import lombok.Data;

/**
 * 公司查看某职位投递信息
 */
@Data
public class JobApplicationDto {
    Integer id;
    Integer resumeId;
    String jobSeekerName; //full name
    String status;
}
