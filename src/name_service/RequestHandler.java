package name_service;

import java.io.IOException;
import java.net.Socket;

/**
 * name_service.RequestHandler Empfaengt/Sendet die Nachrichten von/nach der
 * Middleware
 * 
 */
public class RequestHandler extends Thread {

	private NameService nameService;
	private Socket socket;
	private Connection connection;

	private RequestHandler(NameService nameService, Socket socket) {
		this.nameService = nameService;
		this.socket = socket;
		try {
			this.connection = new Connection(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static RequestHandler init(NameService nameService, Socket socket) {
		return new RequestHandler(nameService, socket);
	}

	@Override
	public void run() {
		try {
			System.out.println(" rq ");

			String request = connection.receive(); 
			System.out.println(request + " sadasd s");
			boolean commandFound = false; 
			String[] requestArr = getCommand(request); 
			String requestCommand = requestArr[0]; 
			String requestParams = requestArr[1]; 
			
			for (Command command : Command.values()){
				if (requestCommand.equals(command.toString().toLowerCase() + " ")){
					System.out.println("ok");
					String response = command.handleInput(requestParams, nameService);
					connection.send(response);
					commandFound = true; 
				}
			}
			System.out.println(request + " ..sssss. ");

			if (!commandFound)
				System.out.println("pppppp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	private String[] getCommand(String request) {
		return request.split("!");
	}
	

}