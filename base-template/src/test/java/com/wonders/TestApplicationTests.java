package org.chen;

import org.chen.service.IOrderTestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
@ComponentScan("org.chen")
@Slf4j
class TestApplicationTests {


    @Autowired
    private IOrderTestService orderTestService;

    @Test
    public void testInsertBySharding(){

        for (int i = 0; i < 100000; i++) {

        }
    }

}
