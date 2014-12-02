package mware_lib;

import java.io.IOException;
import java.net.Socket;

/**
 * DispatcherImpl
 */
public class DispatcherImpl extends Dispatcher{

    private String host;
    private int port;

    public DispatcherImpl(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object sendToSkeleton(Reference reference, String methodName,
                                 Class<?>[] types, Object[] args)  {
        ReturnMethod returnMethod = null;
        CallMethod callMethod = new CallMethod(reference, methodName, args, types);
        Socket socket = null;
        try {
            socket = new Socket(host, port);
            ConnectionObject connectionObject = ConnectionObject.init(socket);

            connectionObject.send(callMethod);
            returnMethod = (ReturnMethod) connectionObject.receive();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
               if (socket != null) {
                 socket.close();
               }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return returnMethod;
    }
}
