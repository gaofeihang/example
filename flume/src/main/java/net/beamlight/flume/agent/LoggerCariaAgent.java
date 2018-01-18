package net.beamlight.flume.agent;

import net.beamlight.flume.FlumeAgent;

import org.apache.flume.Context;
import org.apache.flume.Sink;
import org.apache.flume.sink.LoggerSink;

/**
 * Created on Mar 24, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class LoggerCariaAgent extends AbstractFlumeAgent {

	@Override
    protected Sink createSink() {
	    return new LoggerSink();
    }

	@Override
    protected Context getSinkContext() {
	    return new Context();
    }
	
	public static void main(String[] args) {
		FlumeAgent agent = new LoggerCariaAgent();
		agent.start();
		agent.log("hello, world");
		agent.stop();
    }

}
