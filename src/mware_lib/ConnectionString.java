package mware_lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectionString  {
	private BufferedReader in;
	private OutputStream out;

    public static ConnectionString init(Socket socket) throws IOException {
        return new ConnectionString(socket);
    }

	private ConnectionString(Socket mySock) throws IOException {
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
