package net.beamlight.commons.monitor;

/**
 * Created by gaofeihang on 2017/3/2.
 */
public class MonitorSdkDemo {

    public static void main(String[] args) {
        MonitorSdk.monitor(new MonitoredProcess("my request for abcd1234") {
            @Override
            public boolean execute() {
                doBusinessRequest();
                return true;
            }
        });
    }

    private static void doBusinessRequest() {
    }

}
