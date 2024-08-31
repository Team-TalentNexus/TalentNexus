package org.thirtysix.talentnexus.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class JobApplication {
    private int id;
    private int jobSeekerId;
    private int jobPositionId;
    private Integer resumeId;  // nullable
    private LocalDateTime applicationDate;
    private String status;
    private String feedback;
}

