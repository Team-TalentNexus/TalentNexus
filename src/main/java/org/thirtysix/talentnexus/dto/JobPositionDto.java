package org.thirtysix.talentnexus.dto;

import lombok.Data;

@Data
public class JobPositionDto {
    private int id;
    private String companyName;
    private String title;
    private String description;
    private String location;
    private String employmentType;
    private String salaryRange;
    private boolean active;
}
