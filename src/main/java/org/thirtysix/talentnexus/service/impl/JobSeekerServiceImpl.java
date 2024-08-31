package org.thirtysix.talentnexus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thirtysix.talentnexus.dto.JobSeekerLoginDto;
import org.thirtysix.talentnexus.mapper.JobSeekerMapper;
import org.thirtysix.talentnexus.pojo.JobSeeker;
import org.thirtysix.talentnexus.service.JobSeekerService;
import org.thirtysix.talentnexus.utl.PasswordUtil;
import org.apache.log4j.Logger;

@Service
public class JobSeekerServiceImpl implements JobSeekerService {
    private static final Logger LOGGER = Logger.getLogger(JobSeekerServiceImpl.class);

    @Autowired
    JobSeekerMapper jobSeekerMapper;

    @Override
    public boolean login(JobSeekerLoginDto loginDto) {
        String password = jobSeekerMapper.getPasswordByUsername(loginDto.getUsername());
        System.out.println(password);
        if(password == null) {
            return false;
        }
        return password.equals(PasswordUtil.hashPassword(loginDto.getPassword()));
    }

    @Override
    public String register(JobSeeker seeker) {
        // 字段验证
        if (seeker == null) {
            return "invalid";
        }
        if (!StringUtils.hasText(seeker.getUsername())) {
            return "username_empty";
        }
        if (!StringUtils.hasText(seeker.getPasswordHash())) {
            return "password_empty";
        }
        if (!StringUtils.hasText(seeker.getEmail()) || !isValidEmail(seeker.getEmail())) {
            return "email_invalid";
        }
        if (!StringUtils.hasText(seeker.getPhone())) {
            return "phone_empty";
        }

        // 验证用户名是否已存在
        String username = seeker.getUsername();
        Integer cnt = jobSeekerMapper.getUsernameCount(username);
        if (cnt != 0) {
            return "dup";
        }

        try {
            jobSeekerMapper.insertJobSeeker(seeker);
        } catch (Exception e) {
            LOGGER.error("Error occurred during registration", e);
            return "err";
        }
        return "ok";
    }

    // 简单的电子邮件验证
    private boolean isValidEmail(String email) {
        return email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    }
}
