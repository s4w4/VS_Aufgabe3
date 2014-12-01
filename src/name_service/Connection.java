package name_service;
/**
 * Die Connection Klasse ist aus tcp_advanced.zip  
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Connection {
	private BufferedReader in;
	private OutputStream out;

    public static Connection init(Socket socket) throws IOException {
        return new Connection(socket);
    }

	private Connection(Socket mySock) throws IOException {
		in = new BufferedReader(new InputStreamReader(mySock.getInputStream()));
		out = mySock.getOutputStream();		
	}
	
	public String receive() throws IOException {
		return in.readLine();
	}
	
	public void send(String message) throws IOException {
		out.write((message + "\n").getBytes());
	}
	
	public void close() throws IOException {
		in.close();
		out.close();
	}
}
