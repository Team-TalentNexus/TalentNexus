package org.thirtysix.talentnexus.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Resume {
    private int id;
    private int jobSeekerId;
    private String title;
    private String summary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
