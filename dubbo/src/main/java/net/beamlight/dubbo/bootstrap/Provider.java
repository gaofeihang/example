package net.beamlight.dubbo.bootstrap;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created on Jan 7, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class Provider {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "provider.xml" });
        context.start();

        System.in.read(); // 按任意键退出
    }

}
