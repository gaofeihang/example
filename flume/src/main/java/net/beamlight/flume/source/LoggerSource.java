package net.beamlight.flume.source;

import java.nio.charset.Charset;

import net.beamlight.flume.FlumeLogger;

import org.apache.flume.Event;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.source.AbstractSource;

/**
 * Created on Mar 24, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class LoggerSource extends AbstractSource implements FlumeLogger {
    
    private static final Charset CHARSET = Charset.forName("UTF-8");

	@Override
    public void log(String line) {
		Event event = EventBuilder.withBody(line, CHARSET);
		getChannelProcessor().processEvent(event);
    }

}
