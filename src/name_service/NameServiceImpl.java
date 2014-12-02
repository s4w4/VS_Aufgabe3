package name_service;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;

public class NameServiceImpl extends NameService implements Runnable{

	private int listenPort;
	private Logger logger; 
	private FileHandler fileHandler;
	private ServerSocket serverSocket;
	private ServerSocket listenerSocket;
	private Socket connectionSocket; 
	private Map<String, Reference> nameReferences; 
	
	public NameServiceImpl(int listenPort) {
		nameReferences = new HashMap<String, Reference>(); 
		try {
			fileHandler = new FileHandler("log/Nameservice.log");
			SimpleFormatter simpleFormatter = new SimpleFormatter(); 
			fileHandler.setFormatter(simpleFormatter);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		logger = Logger.getLogger(NameServiceImpl.class.getName() );
		logger.addHandler(fileHandler);
		logger.info("Logger erstellt");
		this.listenPort = listenPort; 
				
		
	}

	@Override
	public synchronized void rebind(String name, Reference reference) {
		logger.info("rebind: " + " name = " + name + ", ip = " + reference.getIp() + ", port = " + reference.getPort() + ", type = " + reference.getType());
		nameReferences.put(name, reference);
	}

	@Override
	public synchronized Reference resolve(String name) {
		logger.info("resolve: name = " + name);
		return nameReferences.get(name); 
	}

	@Override
	public void run() {
		try {

			//Socket erstellen 
			listenerSocket = new ServerSocket(listenPort);
			
			
			while(true) {
				logger.info("Waiting for connection - listening TCP port " + listenPort);
				//Blockiert auf Verbindungsanfrage warten 
				connectionSocket = listenerSocket.accept(); 
				(RequestHandler.init(this, connectionSocket)).start(); 
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}


}
