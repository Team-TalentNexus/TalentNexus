package org.thirtysix.talentnexus.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.thirtysix.talentnexus.dto.ResumeBasicDto;

@Mapper
public interface ResumeMapper {
    @Insert("INSERT INTO resumes(job_seeker_id, title, summary) VALUES(#{jobSeekerId}, #{title}, #{summary})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void create(ResumeBasicDto resumeBasicDto);

    @Select("SELECT job_seeker_id FROM resumes WHERE id = #{id}")
    Integer getJobSeekerIdById(Integer id);
}
