package org.thirtysix.talentnexus.mapper;

import org.apache.ibatis.annotations.*;
import org.thirtysix.talentnexus.pojo.Interview;

@Mapper
public interface InterviewMapper {

    /**
     * 插入新的面试记录
     * @param interview 面试详情
     * @return 受影响的行数
     */
    @Insert("INSERT INTO interviews (company_id, job_seeker_id, job_application_id, interview_link, start_date, end_date) " +
            "VALUES (#{companyId}, #{jobSeekerId}, #{jobApplicationId}, #{interviewLink}, #{startTime}, #{endTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertInterview(Interview interview);

    /**
     * 根据面试ID查询面试信息
     * @param id 面试ID
     * @return 面试详情
     */
    @Select("SELECT * FROM interviews WHERE id = #{id}")
    Interview selectInterviewById(Integer id);

    /**
     * 更新面试记录
     * @param interview 修改后的面试信息
     * @return 受影响的行数
     */
    @Update("UPDATE interviews SET company_id = #{companyId}, job_seeker_id = #{jobSeekerId}, " +
            "job_application_id = #{jobApplicationId}, interview_link = #{interviewLink}, " +
            "start_date = #{startTime}, end_date = #{endTime} WHERE id = #{id}")
    int updateInterview(Interview interview);

    /**
     * 删除面试记录
     * @param id 面试ID
     * @return 受影响的行数
     */
    @Delete("DELETE FROM interviews WHERE id = #{id}")
    int deleteInterview(Integer id);
}

