package net.beamlight.flume.agent;

import java.util.Arrays;

import net.beamlight.flume.FlumeAgent;
import net.beamlight.flume.FlumeLogger;
import net.beamlight.flume.source.LoggerSource;

import org.apache.flume.Channel;
import org.apache.flume.ChannelSelector;
import org.apache.flume.Context;
import org.apache.flume.Sink;
import org.apache.flume.SinkProcessor;
import org.apache.flume.SinkRunner;
import org.apache.flume.Source;
import org.apache.flume.channel.ChannelProcessor;
import org.apache.flume.channel.MemoryChannel;
import org.apache.flume.channel.ReplicatingChannelSelector;
import org.apache.flume.conf.Configurable;
import org.apache.flume.lifecycle.LifecycleState;
import org.apache.flume.lifecycle.LifecycleSupervisor;
import org.apache.flume.lifecycle.LifecycleSupervisor.SupervisorPolicy;
import org.apache.flume.sink.DefaultSinkProcessor;

/**
 * Created on Mar 24, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public abstract class AbstractFlumeAgent implements FlumeAgent {
	
	private Source source;
	private Channel channel;
	
	private LifecycleSupervisor supervisor;
	
	private FlumeLogger flumeLogger;
	
	private void init() {
		source = new LoggerSource();
		channel = new MemoryChannel();
		Sink sink = createSink();
		
		supervisor = new LifecycleSupervisor();
		
		if (channel instanceof Configurable) {
			((Configurable) channel).configure(getChannelContext());
        }
		if (sink instanceof Configurable) {
			((Configurable) sink).configure(getSinkContext());
        }
		
		ChannelSelector selector = new ReplicatingChannelSelector();
        selector.setChannels(Arrays.asList((Channel) channel));
        
        source.setChannelProcessor(new ChannelProcessor(selector));
        
        sink.setChannel(channel);
        
        SinkProcessor sinkProcessor = new DefaultSinkProcessor();
        sinkProcessor.setSinks(Arrays.asList(sink));
        SinkRunner sinkRunner = new SinkRunner(sinkProcessor);
        
        supervisor.supervise(sinkRunner, 
        		new SupervisorPolicy.AlwaysRestartPolicy(), LifecycleState.START);
        
        if (source instanceof FlumeLogger) {
        	flumeLogger = (FlumeLogger) source;
        }
	}
	
	protected Context getChannelContext() {
        Context context = new Context();
        context.put("capacity", "1000");
        context.put("transactionCapacity", "100");
        return context;
    }
	
	protected abstract Sink createSink();
    
	protected abstract Context getSinkContext();

	@Override
    public void log(String line) {
		flumeLogger.log(line);
    }

	@Override
    public void start() {
	    init();
	    channel.start();
	    source.start();
	    supervisor.start();
    }

	@Override
    public void stop() {
	    source.stop();
	    channel.stop();
	    supervisor.stop();
    }

}
