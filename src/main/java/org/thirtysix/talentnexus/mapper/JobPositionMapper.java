package org.thirtysix.talentnexus.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.thirtysix.talentnexus.pojo.JobPosition;

@Mapper
public interface JobPositionMapper {
    @Select("SELECT count(*) FROM job_positions WHERE id = #{id} AND active = true")
    Integer getNumById(Integer id);

    @Select("SELECT title FROM job_positions WHERE id = #{id} AND active = true")
    String getTitleById(Integer id);

    @Insert("INSERT INTO job_positions(company_id, title, description, location, employment_type, salary_range) VALUES (#{companyId}, #{title}, #{description}, #{employmentType}, #{salaryRange})")
    void addJobPosition(JobPosition jobPosition);
}
