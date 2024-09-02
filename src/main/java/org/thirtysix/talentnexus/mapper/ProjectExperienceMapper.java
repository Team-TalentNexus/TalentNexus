package org.thirtysix.talentnexus.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.thirtysix.talentnexus.pojo.ProjectExperience;

@Mapper
public interface ProjectExperienceMapper {
    @Insert("INSERT INTO project_experiences (resume_id, project_name, description, start_date, end_date, role, achievements) " +
            "VALUES (#{resumeId}, #{projectName}, #{description}, #{startDate}, #{endDate}, #{role}, #{achievements})")
    void add(ProjectExperience projectExperience);

}
