package net.beamlight.flume;

/**
 * Created on Mar 24, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public interface FlumeAgent extends FlumeLogger {
	
	void start();
	
	void stop();

}
