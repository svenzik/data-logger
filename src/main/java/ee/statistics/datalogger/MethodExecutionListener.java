package ee.statistics.datalogger;

public interface MethodExecutionListener {
    void notifyOnMethodExecution(MethodExecutionInterceptionResult methodExecutionInterceptionResult);
}