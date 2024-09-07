package org.thirtysix.talentnexus.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.thirtysix.talentnexus.pojo.JobApplication;

import java.util.List;

@Mapper
public interface JobApplicationMapper {
    @Insert("INSERT INTO job_applications(job_seeker_id, job_position_id, resume_id, status) VALUES (#{jobSeekerId}, #{jobPositionId}, #{resumeId}, #{status})")
    void submitApplication(JobApplication jobApplication);

    @Select("SELECT * FROM job_applications WHERE job_seeker_id = #{id} AND active = true LIMIT #{size} OFFSET #{offset}")
    List<JobApplication> getApplicationByJobSeekerId(Integer id, Integer size, Integer offset);

    @Select("SELECT count(*) FROM job_applications WHERE job_seeker_id = #{id} AND active = true")
    Integer getActiveApplicationNumByJobSeekerId(Integer id);

    @Select("SELECT ja.* FROM job_applications ja " +
            "JOIN job_positions jp ON ja.job_position_id = jp.id " +
            "WHERE jp.company_id = #{companyId} AND jp.id = #{jobPositionId} AND ja.active = true LIMIT #{size} OFFSET #{offset}")
    List<JobApplication> getJobApplicationsByCompanyIdAndJobPositionId(Integer companyId, Integer jobPositionId, Integer size, Integer offset);

    @Select("SELECT COUNT(*) FROM job_applications ja " +
            "JOIN job_positions jp ON ja.job_position_id = jp.id " +
            "WHERE jp.company_id = #{companyId} AND jp.id = #{positionId} AND ja.active = true")
    Integer getActiveApplicationNumByCompanyId(Integer companyId, Integer positionId);

    @Select("SELECT job_position_id FROM job_applications WHERE id = #{jobApplicationId}")
    Integer getJobPositionIdById(Integer jobApplicationId);
}
