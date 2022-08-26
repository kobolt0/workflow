package kr.go.spo.workflow.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogbackFilter extends Filter<ILoggingEvent> {
    @Override
    public FilterReply decide(ILoggingEvent event) {
        boolean denyConition = false;
        boolean acceptConition = false;

//        acceptConition = acceptConition || event.getLoggerName().startsWith("kr.go.spo");
        acceptConition= true;

//        denyConition = denyConition || event.getMessage().contains(" ACT_");
//        denyConition = denyConition || event.getLoggerName().contains("selectHistoricVariableInstance");
//        denyConition = denyConition || event.getLoggerName().contains("ActiveMQSession");

        denyConition = false;


        if (denyConition) {
            return FilterReply.DENY;
        }else{
            return FilterReply.ACCEPT;
        }
    }
}

