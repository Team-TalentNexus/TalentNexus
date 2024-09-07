package org.thirtysix.talentnexus.pojo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ProjectExperience {
    private int id;
    private int resumeId;
    private String projectName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String role;
    private String achievements;
}
