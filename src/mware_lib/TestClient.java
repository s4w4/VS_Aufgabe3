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


            for (int i = 0; i < 4; i++){
                Client c = new Client("c"+i );
                c.send("Hi "+i);
                Thread.sleep(1000l);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static class Client{

        private final Socket socket;
        private final String name;

        public static Client init(String name ) throws IOException { return new Client(name );}

        private Client(String name ) throws IOException {
            this.name = name;
            this.socket = new Socket("localhost",5000);
        }

        public void send(String msg) {
            try {
                Connection connection = Connection.init(socket);
                connection.send(msg);
                System.out.println("Client: "+name+" >>> "+msg);
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
