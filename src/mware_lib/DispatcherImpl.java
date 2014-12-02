package mware_lib;

import java.io.IOException;
import java.net.Socket;

/**
 * DispatcherImpl
 */
public class DispatcherImpl implements Dispatcher{

    private String host;
    private int port;

    public DispatcherImpl(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static Dispatcher init(String host, int port) {
        return new DispatcherImpl(host, port);
    }

    public Object sendToSkeleton(Reference reference, String methodName,
                                 Class<?>[] types, Object[] args) throws IOException, ClassNotFoundException {
        ReturnMethod returnMethod = null;
        CallMethod callMethod = new CallMethod(reference, methodName, args, types);
        Socket socket = new Socket(host, port);
        ConnectionObject connectionObject = ConnectionObject.init(socket);

        connectionObject.send(callMethod);
        returnMethod = (ReturnMethod) connectionObject.receive();
        socket.close();
        return returnMethod;
    }
}
