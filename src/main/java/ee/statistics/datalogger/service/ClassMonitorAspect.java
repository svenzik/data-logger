package ee.statistics.datalogger.service;

import ee.statistics.datalogger.ClassMonitor;
import ee.statistics.datalogger.MethodExecutionListener;
import ee.statistics.datalogger.MethodExecutionInterceptionResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;


/**
 * Catches all controller,service and component executions, fetches in/out variables and hands them to registered handlers
 */
@Component
@Aspect
public class ClassMonitorAspect implements ClassMonitor {

    private List<MethodExecutionListener> methodExecutionListeners = new LinkedList<MethodExecutionListener>();

    @Around("execution(* com..*.*(..))")
    public Object handleMethodMessagesAndTiming(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.nanoTime();

        //execute call
        Object retVal = joinPoint.proceed();

        long endTime = System.nanoTime();
        long methodCallDurationInNano = endTime - startTime;

        String methodFullName = joinPoint.getTarget().getClass().getName()+"."+joinPoint.getSignature().getName();

        MethodExecutionInterceptionResult methodExecutionInterceptionResult =
                new MethodExecutionInterceptionResult(methodFullName, joinPoint.getArgs(), retVal, methodCallDurationInNano);

        notifyOnMethodExecution(methodExecutionInterceptionResult);

        return retVal;
    }

    @Override
    public void registerForNotifications(MethodExecutionListener methodExecutionListener) {
        if (methodExecutionListener != null) {
            methodExecutionListeners.add(methodExecutionListener);
        }
    }

    @Override
    public void unregisterForNotifications(MethodExecutionListener methodExecutionListener) {
      methodExecutionListeners.remove(methodExecutionListener);
    }

    private void notifyOnMethodExecution(MethodExecutionInterceptionResult methodExecutionInterceptionResult) {
        for (MethodExecutionListener notifyMe : methodExecutionListeners) {
            notifyMe.notifyOnMethodExecution(methodExecutionInterceptionResult);
        }
    }
}