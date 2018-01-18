package net.beamlight.spring;

import javax.annotation.Resource;

/**
 * Created on Oct 15, 2015
 * @author gaofeihang
 */
public class TestService {

    @Resource
    private TestManager testManager;

    public void hello() {
        testManager.hello();
    }

}
