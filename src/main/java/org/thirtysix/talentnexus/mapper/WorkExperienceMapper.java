package org.thirtysix.talentnexus.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.thirtysix.talentnexus.pojo.WorkExperience;

@Mapper
public interface WorkExperienceMapper {
    @Insert("INSERT INTO work_experiences (resume_id, company_name, position, start_date, end_date, description, achievements) " +
            "VALUES (#{resumeId}, #{companyName}, #{position}, #{startDate}, #{endDate}, #{description}, #{achievements})")
    void add(WorkExperience workExperience);
}
