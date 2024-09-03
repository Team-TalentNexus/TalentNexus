package org.thirtysix.talentnexus.pojo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Resume {
    private int id;
    private int jobSeekerId;
    private String gender;
    private String fullName;
    private String phone;
    private String email;
    private String address;
    private LocalDate birthDate;
    private String title;
    private String summary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ProjectExperience> projectExperiences;
    private List<WorkExperience> workExperiences;
}
