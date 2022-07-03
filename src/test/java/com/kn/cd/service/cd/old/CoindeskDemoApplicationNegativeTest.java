package com.kn.cd.service.cd.old;

import com.kn.cd.CoindeskDemoApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
@Disabled
@SpringBootTest(classes= CoindeskDemoApplication.class, args ={"XYZ"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CoindeskDemoApplicationNegativeTest {

    @Disabled
    @Test
    public void loadContext() {

    }

}
