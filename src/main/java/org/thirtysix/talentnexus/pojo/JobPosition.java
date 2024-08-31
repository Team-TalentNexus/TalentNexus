package org.thirtysix.talentnexus.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class JobPosition {
    private int id;
    private int companyId;
    private String title;
    private String description;
    private String location;
    private String employmentType;
    private String salaryRange;
    private boolean isActive;
    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

