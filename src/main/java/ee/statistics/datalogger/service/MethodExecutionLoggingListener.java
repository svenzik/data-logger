package ee.statistics.datalogger.service;

import ee.statistics.datalogger.MethodExecutionListener;
import ee.statistics.datalogger.MethodExecutionInterceptionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Logs method names, parameters, result objects and time
 */
@Component
public class MethodExecutionLoggingListener implements MethodExecutionListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public MethodExecutionLoggingListener(ClassMonitorAspect classMethodInterceptor) {
        classMethodInterceptor.registerForNotifications(this);
    }

    @Override
    public void notifyOnMethodExecution(MethodExecutionInterceptionResult methodExecutionInterceptionResult) {
        logger.info(methodExecutionInterceptionResult.toString());
    }
}