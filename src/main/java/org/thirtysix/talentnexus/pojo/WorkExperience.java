package org.thirtysix.talentnexus.pojo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class WorkExperience {
    private int id;
    private int resumeId;
    private String companyName;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String achievements;
}

