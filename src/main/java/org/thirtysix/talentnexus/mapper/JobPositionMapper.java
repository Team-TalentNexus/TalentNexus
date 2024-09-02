package org.thirtysix.talentnexus.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface JobPositionMapper {
    @Select("SELECT count(*) FROM job_positions WHERE id = #{id} AND active = true")
    Integer getNumById(Integer id);

    @Select("SELECT title FROM job_positions WHERE id = #{id} AND active = true")
    String getTitleById(Integer id);
}
