package tcp_advanced;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket MySocket;
	private BufferedReader In;
	private OutputStream Out;
	
	public Client(String host, int port) throws UnknownHostException, IOException {
		MySocket = new Socket(host, port);
		
		In = new BufferedReader(new InputStreamReader(MySocket.getInputStream()));
		Out = MySocket.getOutputStream();
	}
	
	public String receive() throws IOException {
		return In.readLine();
	}
	
	public void send(String message) throws IOException {
		Out.write((message + "\n").getBytes());
	}
	
	public void close() throws IOException {
		In.close();
		Out.close();
		MySocket.close();
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		// Verbindung aufbauen
		Client myClient = new Client("localhost", 10002);
		
		// Kommunikation
        //myClient.send("request_rebind ! host; 1; cool; tut");
        myClient.send(  "request_rebind ! Sawa; 54818; mware_lib.ObjectBroker$TestClass; myTestClass");
		//myClient.send("request_resolve ! tut");

		System.out.println(myClient.receive());
		
		// Verbindung schliessen
		myClient.close();
	}

}
