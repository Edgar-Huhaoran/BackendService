package com.hyrax.backend;

import com.hyrax.backend.credential.UserContextHolder;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestJdbcConfig.class, TestServiceConfig.class})
public abstract class TestBase implements InitializingBean, DisposableBean {

    public TestBase() {
        MockitoAnnotations.initMocks(this);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.destroy();
    }

    @Override
    public void destroy() throws Exception {
        UserContextHolder.clearContext();
    }
}