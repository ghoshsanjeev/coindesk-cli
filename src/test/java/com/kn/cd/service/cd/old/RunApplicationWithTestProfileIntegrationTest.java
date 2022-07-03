package com.kn.cd.service.cd.old;

import com.kn.cd.component.CoindeskCLIRunner;
import com.kn.cd.service.cd.CoindeskService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Disabled
@ActiveProfiles("test")
@SpringBootTest
public class RunApplicationWithTestProfileIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Disabled @Test
    void whenContextLoads_thenRunnersAreNotLoaded() {
        assertNotNull(context.getBean(CoindeskService.class));
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> context.getBean(CoindeskCLIRunner.class),
                "CoindeskCLIRunner should not be loaded during this integration test");

    }
}
