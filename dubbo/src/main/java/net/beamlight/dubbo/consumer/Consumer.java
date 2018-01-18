package net.beamlight.dubbo.consumer;

import net.beamlight.commons.util.ByteArrayUtils;
import net.beamlight.dubbo.service.DemoService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created on Jan 7, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "consumer.xml" });
        context.start();

        DemoService demoService = (DemoService) context.getBean("demoService"); // 获取远程服务代理
        String hello = demoService.sayHello("world"); // 执行远程方法

        System.out.println(hello); // 显示调用结果
    }

}
