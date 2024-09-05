package org.thirtysix.talentnexus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thirtysix.talentnexus.mapper.InterviewMapper;
import org.thirtysix.talentnexus.pojo.Interview;
import org.thirtysix.talentnexus.service.EmailService;
import org.thirtysix.talentnexus.service.InterviewService;

@Service
public class InterviewServiceImpl implements InterviewService{

    @Autowired
    private InterviewMapper interviewMapper;

    @Autowired
    private EmailService emailService; // 用于发送邮件通知

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
                "尊敬的求职者，\n\n您已被邀请参加面试。\n\n面试详情：\n公司ID: %d\n职位申请ID: %d\n面试时间: %s - %s\n面试链接: %s",
                interview.getCompanyId(),
                interview.getJobApplicationId(),
                interview.getStartTime(),
                interview.getEndTime(),
                interview.getInterviewLink()
        );
        // 假设求职者的邮箱是通过jobSeekerId获取的
        String jobSeekerEmail = getJobSeekerEmail(interview.getJobSeekerId());
        emailService.sendEmail(jobSeekerEmail, subject, body);
    }

    // 发送面试更新邮件
    private void sendInterviewUpdateEmail(Interview interview) {
        // 邮件内容构造
        String subject = "面试信息更新";
        String body = String.format(
                "尊敬的求职者，\n\n您的面试信息已更新。\n\n面试详情：\n公司ID: %d\n职位申请ID: %d\n面试时间: %s - %s\n面试链接: %s",
                interview.getCompanyId(),
                interview.getJobApplicationId(),
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
                "尊敬的求职者，\n\n您的面试已被取消。\n\n面试详情：\n公司ID: %d\n职位申请ID: %d\n面试时间: %s - %s\n面试链接: %s",
                interview.getCompanyId(),
                interview.getJobApplicationId(),
                interview.getStartTime(),
                interview.getEndTime(),
                interview.getInterviewLink()
        );
        // 假设求职者的邮箱是通过jobSeekerId获取的
        String jobSeekerEmail = getJobSeekerEmail(interview.getJobSeekerId());
        emailService.sendEmail(jobSeekerEmail, subject, body);
    }

    // 根据求职者ID获取求职者邮箱（示例方法，需要根据实际情况实现）
    private String getJobSeekerEmail(Integer jobSeekerId) {
        // 假设有一个服务可以根据ID获取求职者信息
        // 这里需要实现获取求职者邮箱的逻辑
        return "jobseeker@example.com"; // 示例邮箱
    }
}

