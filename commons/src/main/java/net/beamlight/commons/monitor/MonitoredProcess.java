package net.beamlight.commons.monitor;

/**
 * Created by gaofeihang on 2017/3/2.
 */
public abstract class MonitoredProcess implements Monitorable {

    private String resource;

    public MonitoredProcess(String resource) {
        this.resource = resource;
    }

    @Override
    public String getResource() {
        return resource;
    }
}
