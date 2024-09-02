package org.thirtysix.talentnexus.dto;

import lombok.Data;

@Data
public class ResumeBasicDto {
    private Integer id;
    private Integer jobSeekerId;
    private String title;
    private String summary;
}
