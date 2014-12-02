package mware_lib;

import java.io.IOException;

public interface Dispatcher {
    public Object sendToSkeleton(Reference reference, String methodName,Class<?>[] types, Object[] args) throws IOException, ClassNotFoundException;
}
