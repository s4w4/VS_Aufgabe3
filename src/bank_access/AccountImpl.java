package bank_access;

import java.util.logging.Logger;

import mware_lib.Dispatcher;
import mware_lib.Reference;
import mware_lib.ReturnMethod;

/**
 * Created by Alex on 30.11.2014.
 */
public class AccountImpl extends AccountImplBase{

	private Reference reference;
	private Dispatcher dispatcher;
	private Logger logger;

	public AccountImpl(Reference reference) {
		this.reference = reference; 
		this.dispatcher = Dispatcher.init(reference.getIp(), reference.getPort()); 
		this.logger = Logger.getLogger("bankAccess"); 
	}
	
	@Override
	public void transfer(double amount) throws OverdraftException {
		logger.info("transfer: " + amount); 
		String methodName = "transfer"; 
		Class<?>[] types = new Class<?> [] {Double.TYPE}; 
		Object[] args = new Object[] {amount}; 
		ReturnMethod returnMethod = (ReturnMethod) dispatcher.sendToSkeleton(reference, methodName, types, args); 
		Throwable exception = returnMethod.getThrowable(); 
			if (exception != null){
				if (exception instanceof OverdraftException){
					throw (OverdraftException) exception;  
				}
			}
	}

	@Override
	public double getBalance() {
		
		return 0;
	}
	
	
}
