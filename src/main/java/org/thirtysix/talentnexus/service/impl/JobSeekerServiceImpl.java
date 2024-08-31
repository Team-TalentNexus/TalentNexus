package org.thirtysix.talentnexus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thirtysix.talentnexus.dto.JobSeekerLoginDto;
import org.thirtysix.talentnexus.mapper.JobSeekerMapper;
import org.thirtysix.talentnexus.service.JobSeekerService;
import org.thirtysix.talentnexus.utl.PasswordUtil;

@Service
public class JobSeekerServiceImpl implements JobSeekerService {
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
}
