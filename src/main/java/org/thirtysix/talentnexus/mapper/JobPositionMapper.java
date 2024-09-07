package org.thirtysix.talentnexus.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.thirtysix.talentnexus.pojo.JobPosition;

import java.util.List;

@Mapper
public interface JobPositionMapper {
    @Select("SELECT count(*) FROM job_positions WHERE id = #{id} AND active = true")
    Integer getNumById(Integer id);

    @Select("SELECT title FROM job_positions WHERE id = #{id} AND active = true")
    String getTitleById(Integer id);

    @Insert("INSERT INTO job_positions(company_id, title, description, location, employment_type, salary_range) VALUES (#{companyId}, #{title}, #{description}, #{location}, #{employmentType}, #{salaryRange})")
    void addJobPosition(JobPosition jobPosition);

    @Update("UPDATE job_positions set active = false WHERE id = #{id}")
    void deleteById(Integer id);

    @Select("SELECT company_id FROM job_positions WHERE id = #{id}")
    Integer getCompanyIdById(Integer id);

    @Select("SELECT * FROM job_positions WHERE company_id = #{id} LIMIT #{size} OFFSET #{offset}")
    List<JobPosition> getJobPositionsByCompanyId(Integer id, Integer size, Integer offset);
}
