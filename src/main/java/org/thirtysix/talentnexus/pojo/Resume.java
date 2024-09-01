package org.thirtysix.talentnexus.pojo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Resume {
    private int id;
    private int jobSeekerId;
    private String title;
    private String summary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ProjectExperience> projectExperiences;
    private List<WorkExperience> workExperiences;
}
