package org.thirtysix.talentnexus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thirtysix.talentnexus.mapper.HelloMapper;
import org.thirtysix.talentnexus.service.HelloService;

@Service
public class HelloServiceImpl implements HelloService {
    @Autowired
    HelloMapper helloMapper;

    @Override
    public String getMessage(int id) {
        return helloMapper.getMessageById(id);
    }
}
