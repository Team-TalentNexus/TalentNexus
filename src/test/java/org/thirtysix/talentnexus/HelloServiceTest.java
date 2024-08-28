package org.thirtysix.talentnexus;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.thirtysix.talentnexus.mapper.HelloMapper;
import org.thirtysix.talentnexus.service.impl.HelloServiceImpl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class HelloServiceTest {

    @Mock
    private HelloMapper helloMapper;

    @InjectMocks
    private HelloServiceImpl helloService;

    public HelloServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMessage() {
        int id = 1;
        String expectedMessage = "Hello, World!";

        when(helloMapper.getMessageById(id)).thenReturn(expectedMessage);
        String actualMessage = helloService.getMessage(id);
        System.out.println("act = " + actualMessage);
        assertEquals(expectedMessage, actualMessage);
    }
}
