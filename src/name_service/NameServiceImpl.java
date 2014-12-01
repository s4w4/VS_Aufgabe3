package name_service;
import java.io.IOException;
import java.util.logging.*;

public class NameServiceImpl extends NameService {

	private int port;
	private Logger logger; 
	private FileHandler fileHandler; 
	
	public NameServiceImpl(int port) {
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
		this.port = port; 
		
	}

	@Override
	public void rebind(String name, Reference reference) {
		// TODO Auto-generated method stub

		logger.info("rebind");
	}

	@Override
	public Reference resolve(String name) {
		// TODO Auto-generated method stub
		return null;
	}


}
