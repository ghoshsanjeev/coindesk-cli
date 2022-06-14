package com.kn.cd;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes=CoindeskDemoApplication.class, args ={"GBP"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CoindeskDemoApplicationTests {

    @Test
    public void loadContext() {

    }

}
