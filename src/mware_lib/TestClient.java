package mware_lib;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by Alex on 01.12.2014.
 */
public class TestClient {
    public static void main(String[] args){

        try {
            Socket s = new Socket("localhost",5000);

            for (int i = 0; i < 4; i++){
                Client c = new Client("c"+i, s);
                c.send("Hi "+i);
                Thread.sleep(1000l);
            }
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static class Client{

        private final Socket socket;
        private final String name;

        public static Client init(String name, Socket socket){ return new Client(name, socket);}

        private Client(String name, Socket socket){
            this.name = name;
            this.socket = socket;
        }

        public void send(String msg) {
            try {
                Connection connection = Connection.init(socket);
                connection.send(msg);
                System.out.println("Client: "+name+" >>> "+msg);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
