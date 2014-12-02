package bank_access;

import java.util.logging.Logger;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;
import mware_lib.Dispatcher;
import mware_lib.Reference;
import mware_lib.ReturnMethod;

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
				reference.getPort());
		this.logger = Logger.getLogger("bankAccess");
	}

	@Override
	public String createAccount(String owner, String branch)
			throws InvalidParamException {
		logger.info("ManagerImpl: createAccount(" + "owner = " + owner + ", branch = "
				+ branch +")");
		String methodName = "createAccount";
		Class<?>[] types = new Class<?>[] { String.class, String.class };
		Object[] args = new Object[] { owner, branch };
		ReturnMethod returnMethod = (ReturnMethod) dispatcher.sendToSkeleton(
				reference, methodName, types, args);
		Throwable exception = returnMethod.getThrowable();
		if (exception != null) {
			if (exception instanceof InvalidParamException) {
				logger.info("ManagerImpl: createAccount(" + "owner = " + owner + ", branch = "
						+ branch +") -> InvalidParamException");
				throw (InvalidParamException) exception;
			}
		}
		String result = (String) returnMethod.getReturnValue(); 
		logger.info("ManagerImpl: createAccount(" + "owner = " + owner + ", branch = "
				+ branch +") -> "+result);
		return result;
	}
}
