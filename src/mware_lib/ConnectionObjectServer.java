package mware_lib;

import tcp_advanced.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

public class ConnectionObjectServer  {
    private ServerSocket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public static ConnectionObjectServer init(ServerSocket socket) throws IOException {
        return new ConnectionObjectServer(socket);
    }

    private ConnectionObjectServer(ServerSocket socket) throws IOException {
        this.socket = socket;
    }

    public Object receive() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        Object object = objectInputStream.readObject();
        return object;
    }

    public void send(Object message) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(message);
    }

    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
