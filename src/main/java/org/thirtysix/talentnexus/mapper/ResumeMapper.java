package org.thirtysix.talentnexus.mapper;

import org.apache.ibatis.annotations.*;
import org.thirtysix.talentnexus.dto.ResumeBasicDto;
import org.thirtysix.talentnexus.pojo.Resume;

@Mapper
public interface ResumeMapper {
    @Insert("INSERT INTO resumes(job_seeker_id, title, summary) VALUES(#{jobSeekerId}, #{title}, #{summary})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void create(ResumeBasicDto resumeBasicDto);

    @Select("SELECT job_seeker_id FROM resumes WHERE id = #{id}")
    Integer getJobSeekerIdById(Integer id);

    @Select("SELECT id, job_seeker_id, title, summary, created_at, updated_at FROM resumes WHERE job_seeker_id = #{jobSeekerId}")
    Resume getResumeByJobSeekerId(Integer jobSeekerId);

    @Select("SELECT id FROM resumes WHERE job_seeker_id = #{id}")
    Integer getIdByJobSeekerId(Integer id);

    @Delete("DELETE FROM resumes WHERE job_seeker_id = #{id}")
    void deleteResumeByJobSeekerId(Integer id);

    @Select("SELECT COUNT(*) > 0 AS is_visible " +
            "FROM resumes r " +
            "JOIN job_applications ja ON r.id = ja.resume_id " +
            "JOIN job_positions jp ON ja.job_position_id = jp.id " +
            "WHERE r.id = #{resumeId} " +
            "AND jp.company_id = #{companyId} " +
            "AND ja.active = TRUE")
    boolean isResumeVisibleToCompany(Integer resumeId, Integer companyId);
}
