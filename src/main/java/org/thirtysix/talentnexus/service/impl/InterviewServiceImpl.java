package org.thirtysix.talentnexus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thirtysix.talentnexus.mapper.CompanyMapper;
import org.thirtysix.talentnexus.mapper.InterviewMapper;
import org.thirtysix.talentnexus.mapper.JobApplicationMapper;
import org.thirtysix.talentnexus.pojo.Interview;
import org.thirtysix.talentnexus.pojo.JobSeeker;
import org.thirtysix.talentnexus.service.EmailService;
import org.thirtysix.talentnexus.service.InterviewService;
import org.thirtysix.talentnexus.service.JobPositionService;
import org.thirtysix.talentnexus.service.JobSeekerService;

@Service
public class InterviewServiceImpl implements InterviewService{

    @Autowired
    private InterviewMapper interviewMapper;

    @Autowired
    private EmailService emailService; // 用于发送邮件通知

    @Autowired
    private JobSeekerService jobSeekerService;//用于根据id查询邮箱

    @Autowired
    private JobPositionService jobPositionService;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private JobApplicationMapper jobApplicationMapper;

    // 根据公司ID获取公司名称
    public String getCompanyName(Integer companyId) {
        return companyMapper.getCompanyNameById(companyId);
    }

    // 根据职位申请ID获取职位名称
    public String getJobApplicationTitle(Integer jobApplicationId) {
        Integer jobId = jobApplicationMapper.getJobPositionIdById(jobApplicationId);
        return jobPositionService.getTitleById(jobId);
    }

    /**
     * 创建面试
     * @param interview 面试详情
     * @return 面试ID
     */
    public Integer createInterview(Interview interview) {
        // 保存面试记录到数据库
        int rowsAffected = interviewMapper.insertInterview(interview);
        if (rowsAffected > 0) {
            // 发送面试邀请邮件
            sendInterviewInvitationEmail(interview);
            return interview.getId();
        }
        return null;
    }

    /**
     * 根据ID获取面试信息
     * @param id 面试ID
     * @return 面试信息
     */
    public Interview getInterviewById(Integer id) {
        return interviewMapper.selectInterviewById(id);
    }

    /**
     * 更新面试信息
     * @param interview 修改后的面试信息
     * @return 是否更新成功
     */
    public boolean updateInterview(Interview interview) {
        int rowsAffected = interviewMapper.updateInterview(interview);
        if (rowsAffected > 0) {
            // 发送面试修改邮件
            sendInterviewUpdateEmail(interview);
            return true;
        }
        return false;
    }

    /**
     * 删除面试
     * @param id 面试ID
     * @return 是否删除成功
     */
    public boolean deleteInterview(Integer id) {
        Interview interview = interviewMapper.selectInterviewById(id);
        if (interview != null) {
            int rowsAffected = interviewMapper.deleteInterview(id);
            if (rowsAffected > 0) {
                // 发送面试删除邮件
                sendInterviewDeletionEmail(interview);
                return true;
            }
        }
        return false;
    }

    // 发送面试邀请邮件
    private void sendInterviewInvitationEmail(Interview interview) {
        // 邮件内容构造
        String subject = "面试邀请";
        String body = String.format(
                "尊敬的求职者，\n\n您已被邀请参加面试。\n\n面试详情：\n公司名称: %s\n职位名称: %s\n面试时间: %s - %s\n面试链接: %s",
                getCompanyName(interview.getCompanyId()),
                getJobApplicationTitle(interview.getJobApplicationId()),
                interview.getStartTime(),
                interview.getEndTime(),
                interview.getInterviewLink()
        );
        // 求职者的邮箱是通过jobSeekerId获取的
        String jobSeekerEmail = getJobSeekerEmail(interview.getJobSeekerId());
        emailService.sendEmail(jobSeekerEmail, subject, body);
    }

    // 发送面试更新邮件
    private void sendInterviewUpdateEmail(Interview interview) {
        // 邮件内容构造
        String subject = "面试信息更新";
        String body = String.format(
                "尊敬的求职者，\n\n您的面试信息已更新。\n\n面试详情：\n公司名称: %s\n职位名称: %s\n面试时间: %s - %s\n面试链接: %s",
                getCompanyName(interview.getCompanyId()),
                getJobApplicationTitle(interview.getJobApplicationId()),
                interview.getStartTime(),
                interview.getEndTime(),
                interview.getInterviewLink()
        );
        // 假设求职者的邮箱是通过jobSeekerId获取的
        String jobSeekerEmail = getJobSeekerEmail(interview.getJobSeekerId());
        emailService.sendEmail(jobSeekerEmail, subject, body);
    }

    // 发送面试删除邮件
    private void sendInterviewDeletionEmail(Interview interview) {
        // 邮件内容构造
        String subject = "面试删除通知";
        String body = String.format(
                "尊敬的求职者，\n\n您的面试已被取消。\n\n面试详情：\n公司名称: %s\n职位名称: %s\n面试时间: %s - %s\n面试链接: %s",
                getCompanyName(interview.getCompanyId()),
                getJobApplicationTitle(interview.getJobApplicationId()),
                interview.getStartTime(),
                interview.getEndTime(),
                interview.getInterviewLink()
        );
        // 求职者的邮箱是通过jobSeekerId获取的
        String jobSeekerEmail = getJobSeekerEmail(interview.getJobSeekerId());
        emailService.sendEmail(jobSeekerEmail, subject, body);
    }

    // 根据求职者ID获取求职者邮箱
    private String getJobSeekerEmail(Integer jobSeekerId) {
        JobSeeker jobSeeker4 = jobSeekerService.getById(jobSeekerId);
        return jobSeeker4 != null ? jobSeeker4.getEmail() : null;
    }
}

