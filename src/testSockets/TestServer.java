package testSockets;

import mware_lib.ConnectionString;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by Alex on 01.12.2014.
 */
public class TestServer {

    private static final int port = 5000;
    private static final int POOL_SIZE = 4;
    private static final int MAX_INCOMMING_CONNECTIONS = 2;

    public static void main(String[] args){

        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });

        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            //ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //serverSocketChannel.socket().bind(new InetSocketAddress(port),MAX_INCOMMING_CONNECTIONS);
            while (true){
                pool.execute(Server.init(serverSocket.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static class Server implements Runnable{

        private final Socket socket;

        public static Server init(Socket socket){ return new Server(socket);}

        private Server(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            /*try {
                Connection connectionString = ConnectionString.init(socket);
                String receive = connectionString.receive();
                System.out.println("SERVER : " +receive+" >>> "+this.hashCode());
                Thread.sleep(10l);
                connectionString.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

        }

    }



}
