package mware_lib;

public abstract class Dispatcher {

    public static Dispatcher init(String host, int port) {
        return new DispatcherImpl(host, port);
    }

    public abstract Object sendToSkeleton(Reference reference, String methodName,Class<?>[] types, Object[] args);

}
