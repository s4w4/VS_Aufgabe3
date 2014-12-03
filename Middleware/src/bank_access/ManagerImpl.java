package bank_access;

import mware_lib.Dispatcher;
import mware_lib.Reference;
import mware_lib.ReturnMethod;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Alex on 30.11.2014.
 */
public class ManagerImpl extends ManagerImplBase {

	private Reference reference;
	private Dispatcher dispatcher;
	private Logger logger;

	public ManagerImpl(Reference reference) {
		this.reference = reference;
		this.dispatcher = Dispatcher.init(reference.getIp(),
				reference.getPort(), logger);
		this.logger = Logger.getLogger("bankAccess");
        logger.setLevel(Level.OFF);
	}

	@Override
	public String createAccount(String owner, String branch)
			throws InvalidParamException {
		logger.log(Level.INFO,"ManagerImpl: createAccount(" + "owner = " + owner + ", branch = "
				+ branch +")");
		String methodName = "createAccount";
		Class<?>[] types = new Class<?>[] { String.class, String.class };
		Object[] args = new Object[] { owner, branch };
        logger.log(Level.INFO,"SendToSkeleton Anfang");
		ReturnMethod returnMethod = (ReturnMethod) dispatcher.sendToSkeleton(
				reference, methodName, types, args);
        logger.log(Level.INFO,"SendToSkeleton Ende");

        Throwable exception = returnMethod.getThrowable();
		if (exception != null) {
			if (exception instanceof InvalidParamException) {
				logger.info("ManagerImpl: createAccount(" + "owner = " + owner + ", branch = "
						+ branch +") -> InvalidParamException");
				throw (InvalidParamException) exception;
			}
		}
		String result = (String) returnMethod.getReturnValue(); 
		logger.log(Level.INFO,"ManagerImpl: createAccount(" + "owner = " + owner + ", branch = "
				+ branch +") -> "+result);
		return result;
	}
}
