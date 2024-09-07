package org.thirtysix.talentnexus.service.impl;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thirtysix.talentnexus.dto.CompanyLoginDto;
import org.thirtysix.talentnexus.mapper.CompanyMapper;
import org.thirtysix.talentnexus.pojo.Company;
import org.thirtysix.talentnexus.service.CompanyService;
import org.thirtysix.talentnexus.util.PasswordUtil;
import org.apache.logging.log4j.Logger;

@Service
public class CompanyServiceImpl implements CompanyService {
    private static final Logger LOGGER = LogManager.getLogger(CompanyServiceImpl.class);

    @Autowired
    CompanyMapper companyMapper;

    @Override
    public boolean login(CompanyLoginDto loginDto) {
        String password = companyMapper.getPasswordByUsername(loginDto.getUsername());
        if (password == null) {
            return false;
        }
        return password.equals(PasswordUtil.hashPassword(loginDto.getPassword()));
    }

    @Override
    public String register(Company company) {
        // 字段验证
        if (company == null) {
            return "invalid";
        }
        if (!StringUtils.hasText(company.getUsername())) {
            return "username_empty";
        }
        if (!StringUtils.hasText(company.getPassword())) {
            return "password_empty";
        }
        if (!StringUtils.hasText(company.getEmail()) || !isValidEmail(company.getEmail())) {
            return "email_invalid";
        }
        if (!StringUtils.hasText(company.getPhoneNumber())) {
            return "phone_empty";
        }

        // 验证用户名是否已存在
        String username = company.getUsername();
        Integer cnt = companyMapper.getUsernameCount(username);
        if (cnt != 0) {
            return "dup";
        }

        // 验证邮箱是否已存在
        String email = company.getEmail();
        cnt = companyMapper.getEmailCount(email);
        if (cnt != 0) {
            return "dup";
        }

        // 密码加密
        company.setPassword(PasswordUtil.hashPassword(company.getPassword()));

        try {
            companyMapper.insertCompany(company);
        } catch (Exception e) {
            LOGGER.error("Error occurred during registration", e);
            return "err";
        }
        return "ok";
    }

    @Override
    public Company getByUsername(String username) {
        return companyMapper.getCompanyByUsername(username);
    }

    @Override
    public Integer getCompanyIdByUsername(String username) {
        return getByUsername(username).getId();
    }

    // 简单的电子邮件验证
    private boolean isValidEmail(String email) {
        return email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    }
}
