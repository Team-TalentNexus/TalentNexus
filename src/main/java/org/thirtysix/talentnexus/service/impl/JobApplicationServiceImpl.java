package org.thirtysix.talentnexus.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thirtysix.talentnexus.mapper.CompanyMapper;
import org.thirtysix.talentnexus.mapper.JobApplicationMapper;
import org.thirtysix.talentnexus.mapper.JobPositionMapper;
import org.thirtysix.talentnexus.mapper.JobSeekerMapper;
import org.thirtysix.talentnexus.pojo.Company;
import org.thirtysix.talentnexus.pojo.JobApplication;
import org.thirtysix.talentnexus.pojo.JobSeeker;
import org.thirtysix.talentnexus.service.CompanyService;
import org.thirtysix.talentnexus.service.JobApplicationService;
import org.thirtysix.talentnexus.service.EmailService;
import org.thirtysix.talentnexus.service.JobSeekerService;

import java.util.List;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {
    private static final Logger LOGGER = LogManager.getLogger(JobApplicationServiceImpl.class);

    @Autowired
    JobApplicationMapper jobApplicationMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private JobSeekerMapper jobSeekerMapper;

    @Autowired
    private JobPositionMapper jobPositionMapper;

    @Override
    public boolean submitApplication(JobApplication jobApplication) {
        try {
            jobApplicationMapper.submitApplication(jobApplication);
            sendAppicationEmail(jobApplication);
        } catch (Exception e) {
            LOGGER.error("Failed to submit an application: {}", e.getMessage());
            return false;
        }

        return true;
    }

    public String getCompanyName(Integer companyId){
        return companyMapper.getCompanyNameById(companyId);
    }

    public String getJobSeekerName(Integer jobSeekerId){
        return jobSeekerMapper.getFullNameById(jobSeekerId);
    }

    public String getPositionName(Integer positionId) {
        return jobPositionMapper.getTitleById(positionId);
    }

    public Integer getCompanyId(Integer jobPositionId){
        return jobPositionMapper.getCompanyIdById(jobPositionId);
    }

    // 根据公司ID获取求职者邮箱
    private String getCompanyEmail(Integer companyId) {
        return companyMapper.getCompanyNameById(companyId);
    }

    private void sendAppicationEmail(JobApplication jobApplication) {
        // 邮件内容构造
        String subject = "职位申请";
        String body = String.format(
                "尊敬的%s公司，\n\n我的职位申请已经投递。\n\n申请详情：\n申请人: %s\n申请职位名称: %s\n简历Id: %d",
                getCompanyName(getCompanyId(jobApplication.getJobPositionId())),
                getJobSeekerName(jobApplication.getJobSeekerId()),
                getPositionName(jobApplication.getJobPositionId()),
                jobApplication.getResumeId()
        );
        // 公司的邮箱是通过companyId获取的
        String jobSeekerEmail = getCompanyEmail(getCompanyId(jobApplication.getJobPositionId()));
        emailService.sendEmail(jobSeekerEmail, subject, body);
    }

    @Override
    public List<JobApplication> getApplicationByJobSeekerId(Integer id, Integer page, Integer size) {
        try {
            return jobApplicationMapper.getApplicationByJobSeekerId(id, size, (page - 1) * size);
        } catch (Exception e) {
            LOGGER.error("Error occurred while retrieving job applications for job seeker ID: {}", id, e);
            throw new RuntimeException("Failed to retrieve job applications for job seeker ID: " + id, e);
        }
    }

    @Override
    public Integer getActiveApplicationNumByJobSeekerId(Integer id) {
        return jobApplicationMapper.getActiveApplicationNumByJobSeekerId(id);
    }

    @Override
    public List<JobApplication> getApplicationsByCompanyIdAndJobPositionId(Integer companyId, Integer positionId, Integer page, Integer size) {
        try {
            return jobApplicationMapper.getJobApplicationsByCompanyIdAndJobPositionId(companyId, positionId, size, (page - 1) * size);
        } catch (Exception e) {
            LOGGER.error("Error occurred while retrieving job applications for company ID: {}", companyId, e);
            throw new RuntimeException("Failed to retrieve job applications for company ID: " + companyId, e);
        }
    }

    @Override
    public Integer getActiveApplicationNumByCompanyId(Integer id, Integer positionId) {
        return jobApplicationMapper.getActiveApplicationNumByCompanyId(id, positionId);
    }

    @Override
    public Integer getCountBySeekerIdAndPositionId(Integer jobSeekerId, Integer jobPositionId) {
        return jobApplicationMapper.getCountBySeekerIdAndPositionId(jobSeekerId, jobPositionId);
    }
}
