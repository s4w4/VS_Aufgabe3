package testSockets;

import mware_lib.ConnectionString;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 01.12.2014.
 */
public class TestClient {
    public static void main(String[] args){

        try {

            List<Client> clientList = new ArrayList<Client>();
            for (int i = 0; i < 20; i++){
                Client c = new Client("c"+i, "MSG"+i);
                (new Thread(c)).start();
                clientList.add(c);
            }

        } catch (IOException e) {
            e.printStackTrace();
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        }
    }


    public static class Client implements Runnable{

        private final Socket socket;
        private final String name;
        private final String msg;

        public static Client init(String name,String msg) throws IOException { return new Client(name,msg );}

        private Client(String name,String msg ) throws IOException {
            this.name = name;
            this.msg = msg;
            this.socket = new Socket("localhost",5000);
        }

        public void send() {
            try {
                ConnectionString connectionString = ConnectionString.init(socket);
                connectionString.send(msg);
                System.out.println("Client: "+name+" >>> "+msg);
                connectionString.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            send();
        }
    }
}
