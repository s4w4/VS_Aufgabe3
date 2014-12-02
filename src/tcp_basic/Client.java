package tcp_basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Refresher zur TCP-Programmierung (Clientseite)
 *
 */
public class Client {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Socket mySock;
		BufferedReader in;
		OutputStream out;		
		
		// Verbindung aufbauaen
		mySock = new Socket("localhost", 5002);
		
		// I/O-Kanäle der Socket
		in = new BufferedReader(new InputStreamReader(mySock.getInputStream()));
		out = mySock.getOutputStream();
		
		// Kommunikation
		out.write(("request_rebind! host 1 cool tut").getBytes());
		System.out.println(in.readLine());
		
		// Verbindung schliessen
		in.close();
		out.close();
		mySock.close();
	}

}
