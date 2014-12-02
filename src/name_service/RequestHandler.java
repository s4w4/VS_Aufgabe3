package name_service;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * name_service.RequestHandler Empfaengt/Sendet die Nachrichten von/nach der
 * Middleware
 * 
 */
public class RequestHandler extends Thread {

	private NameService nameService;
	private Socket socket;
	private Connection connection;
    private Logger logger;

	private RequestHandler(NameService nameService, Socket socket, Logger logger) {
		this.nameService = nameService;
		this.socket = socket;
        this.logger = logger;
		try {
			this.connection = new Connection(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static RequestHandler init(NameService nameService, Socket socket, Logger logger) {
		return new RequestHandler(nameService, socket, logger);
	}

	@Override
	public void run() {
		try {
			String request = connection.receive(); 
			boolean commandFound = false; 
			String[] requestArr = getCommand(request); 
			String requestCommand = requestArr[0]; 
			String requestParams = requestArr[1]; 
			
			for (Command command : Command.values()){
				if (requestCommand.equals(command.toString().toLowerCase() + " ")){
					String response = command.handleInput(requestParams, nameService, logger);
					connection.send(response);
					commandFound = true; 
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	private String[] getCommand(String request) {
		return request.split("!");
	}
	

}