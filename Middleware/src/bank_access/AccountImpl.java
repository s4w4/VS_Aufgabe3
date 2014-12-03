package bank_access;

import mware_lib.Dispatcher;
import mware_lib.Reference;
import mware_lib.ReturnMethod;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AccountImpl
 */
public class AccountImpl extends AccountImplBase {

	private Reference reference;
	private Dispatcher dispatcher;
	private Logger logger;

	public AccountImpl(Reference reference) {
		this.reference = reference;
		this.dispatcher = Dispatcher.init(reference.getIp(),
				reference.getPort(), logger);
		this.logger = Logger.getLogger("bankAccess");
        logger.setLevel(Level.OFF);
	}

	@Override
	public void transfer(double amount) throws OverdraftException {
		logger.log(Level.INFO,"AccountImpl: transfer(" + amount + ")");
		String methodName = "transfer";
		Class<?>[] types = new Class<?>[] { Double.TYPE };
		Object[] args = new Object[] { amount };
		ReturnMethod returnMethod;
		returnMethod = (ReturnMethod) dispatcher.sendToSkeleton(reference,
				methodName, types, args);
		Throwable exception = returnMethod.getThrowable();
		if (exception != null) {
			if (exception instanceof OverdraftException) {
				logger.log(Level.INFO,"AccountImpl: transfer(" + amount + ") -> OverdraftException");
				throw (OverdraftException) exception;
			}
		}
	}

	@Override
	public double getBalance() {
		logger.log(Level.INFO,"AccountImpl: getBalance()");
		String methodName = "getBalance";
		Class<?>[] types = new Class<?>[] {};
		Object[] args = new Object[] {};
		ReturnMethod returnMethod = (ReturnMethod) dispatcher.sendToSkeleton(
				reference, methodName, types, args);
		double result = (Double) returnMethod.getReturnValue(); 
		logger.log(Level.INFO,"AccountImpl: getBalance()->"+result);

		return result;
	}

}
