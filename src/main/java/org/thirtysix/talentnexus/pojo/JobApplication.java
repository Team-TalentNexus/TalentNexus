package org.thirtysix.talentnexus.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class JobApplication {
    private Integer id;
    private Integer jobSeekerId;
    private Integer jobPositionId;
    private Integer resumeId;
    private LocalDateTime applicationDate;
    private String status;
    private boolean active;
    private String feedback;
}
