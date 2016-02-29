package ee.statistics.datalogger;

/**
 * This class helps buid a method inseption object.
 * toString is used for logging and values are used for persisting statistics
 */
public class MethodExecutionInterceptionResult {

    public MethodExecutionInterceptionResult(String methodAndClassFullName,
                                             Object[] methodParameters,
                                             Object returnValue,
                                             long methodTimeInNanoseconds) {
        this.methodAndClassFullName = methodAndClassFullName;
        this.methodParameters = methodParameters;
        this.returnValue = returnValue;
        this.methodTimeInNanoseconds = methodTimeInNanoseconds;
    }

    private String methodAndClassFullName;
    private Object [] methodParameters;
    private Object returnValue;
    private long methodTimeInNanoseconds;

    public String getMethodAndClassFullName() {
        return methodAndClassFullName;
    }

    public long getMethodTimeInNanoseconds() {
        return methodTimeInNanoseconds;
    }

    @Override
    public String toString() {
        //TODO: create a sparate class for formatting
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("[%5.2f ms] - ", methodTimeInNanoseconds / 1000000.0));
        stringBuilder.append(methodAndClassFullName);

        stringBuilder.append("(");

        // append args
        Object[] args = methodParameters;
        for (Object methodArgument : args) {
            stringBuilder.append(methodArgument).append(",");
        }
        if (args.length > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        //append result data
        stringBuilder.append(") -> ");
        stringBuilder.append(returnValue);

        return stringBuilder.toString();
    }
}