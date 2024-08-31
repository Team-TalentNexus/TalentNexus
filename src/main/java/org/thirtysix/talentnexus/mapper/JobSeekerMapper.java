package org.thirtysix.talentnexus.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.thirtysix.talentnexus.pojo.JobSeeker;

@Mapper
public interface JobSeekerMapper {
    @Select("SELECT password_hash FROM job_seekers WHERE username = #{username}")
    String getPasswordByUsername(@Param("username") String username);

    @Insert("INSERT INTO job_seekers (username, password_hash, email, phone, full_name, gender, birth_date, address) " +
            "VALUES (#{username}, #{passwordHash}, #{email}, #{phone}, #{fullName}, #{gender}, #{birthDate}, #{address})")
    void insertJobSeeker(JobSeeker jobSeeker);

    @Select("SELECT count(*) FROM job_seekers WHERE username = #{username}")
    Integer getUsernameCount(String username);
}
