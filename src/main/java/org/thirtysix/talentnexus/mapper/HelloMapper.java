package org.thirtysix.talentnexus.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HelloMapper {
    @Select("select message from hello where id = #{id}")
    String getMessageById(int id);
}
