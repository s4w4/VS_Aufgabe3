package mware_lib;

import java.util.logging.Logger;

public abstract class Dispatcher {

    public static Dispatcher init(String host, int port, Logger logger) {
        return new DispatcherImpl(host, port, logger);
    }

    public abstract Object sendToSkeleton(Reference reference, String methodName,Class<?>[] types, Object[] args);

}
