package org.thirtysix.talentnexus.mapper;

import org.apache.ibatis.annotations.Select;

public interface JobSeekerMapper {
    @Select("SELECT password_hash FROM job_seekers WHERE username = #{username}")
    String login(String username);
}
