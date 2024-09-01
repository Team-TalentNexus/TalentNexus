package org.thirtysix.talentnexus.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.thirtysix.talentnexus.dto.ResumeBasicDto;

@Mapper
public interface ResumeMapper {
    @Insert("INSERT INTO resumes(job_seeker_id, title, summary) VALUES(#{jobSeekerId}, #{title}, #{summary})")
    void create(ResumeBasicDto resumeBasicDto);


}
