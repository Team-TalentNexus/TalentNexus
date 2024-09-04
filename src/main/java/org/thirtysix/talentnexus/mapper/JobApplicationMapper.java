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
}
