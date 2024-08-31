package org.thirtysix.talentnexus.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface JobSeekerMapper {
    @Select("SELECT password_hash FROM job_seekers WHERE username = #{username}")
    String getPasswordByUsername(@Param("username") String username);
}
