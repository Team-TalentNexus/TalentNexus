package org.thirtysix.talentnexus.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HelloMapper {
    @Select("select message from hello where id = #{id}")
    @Results(id = "helloResultMap", value = {
            @Result(column = "message", property = "m_message"),
    })
    String getMessageById(int id);
}
