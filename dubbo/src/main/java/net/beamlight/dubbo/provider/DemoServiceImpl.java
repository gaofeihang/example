package net.beamlight.dubbo.provider;

import net.beamlight.dubbo.service.DemoService;

/**
 * Created on Jan 7, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class DemoServiceImpl implements DemoService {
 
    public String sayHello(String name) {
        return "Hello " + name;
    }
 
}
