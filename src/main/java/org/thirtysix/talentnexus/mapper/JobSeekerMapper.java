package org.thirtysix.talentnexus.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.thirtysix.talentnexus.pojo.JobSeeker;

@Mapper
public interface JobSeekerMapper {
    @Select("SELECT password FROM job_seekers WHERE username = #{username}")
    String getPasswordByUsername(@Param("username") String username);

    @Insert("INSERT INTO job_seekers (username, password, email, phone, full_name, gender, birth_date, address, university, major, degree) " +
            "VALUES (#{username}, #{password}, #{email}, #{phone}, #{fullName}, #{gender}, #{birthDate}, #{address}, #{university}, #{major}, #{degree})")
    void insertJobSeeker(JobSeeker jobSeeker);

    @Select("SELECT count(*) FROM job_seekers WHERE username = #{username}")
    Integer getUsernameCount(String username);

    @Select("SELECT count(*) FROM job_seekers WHERE email = #{email}")
    Integer getEmailCount(String email);

    @Select("SELECT * FROM job_seekers WHERE username = #{username}")
    JobSeeker getJobSeekerByUsername(String username);

    @Select("SELECT * FROM job_seekers WHERE id = #{id}")
    JobSeeker getJobSeekerById(Integer id);

    @Select("SELECT full_name FROM job_seekers WHERE id = #{id}")
    String getFullNameById(Integer id);
}
