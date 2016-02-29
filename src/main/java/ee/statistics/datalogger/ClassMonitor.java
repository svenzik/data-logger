package ee.statistics.datalogger;

public interface ClassMonitor {
	void registerForNotifications(MethodExecutionListener methodExecutionListener);

	void unregisterForNotifications(MethodExecutionListener methodExecutionListener);
}
