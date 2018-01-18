package net.beamlight.spring.context;

import net.beamlight.spring.TestService;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created on Oct 15, 2015
 * @author gaofeihang
 */
public class SpringContextTest {
    
    @Test
    public void testContext() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        
        TestService service = (TestService) context.getBean("testService");
        service.hello();
        
        context.close();
    }

}
