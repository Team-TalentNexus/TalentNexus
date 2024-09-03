package org.thirtysix.talentnexus.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Interview {
    private Integer id;
    private Integer jobSeekerId;
    private Integer companyId;
    private Integer jobApplicationId;
    private String interviewLink;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String feedback;
}
