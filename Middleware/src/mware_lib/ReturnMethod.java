package mware_lib;

import java.io.Serializable;

/**
 * ReturnMethod.
 */
public class ReturnMethod implements Serializable{
    private final Throwable throwable;
    private Object returnValue;

    public ReturnMethod(Object returnValue, Throwable throwable) {
        this.returnValue = returnValue;
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }
}
