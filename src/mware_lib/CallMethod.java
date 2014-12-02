package mware_lib;

/**
 * Created by Alex on 02.12.2014.
 */
public class CallMethod {
    private Reference reference;
    private String methodName;
    private Object[] args;
    private Class<?>[] types;

    public Reference getReference() {
        return reference;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public Class<?>[] getTypes() {
        return types;
    }
}
