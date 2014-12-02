package cash_access;

import java.util.logging.Logger;

import mware_lib.Dispatcher;
import mware_lib.Reference;
import mware_lib.ReturnMethod;

/**
 * cash_access.TransactionImpl
 */
public class TransactionImpl extends TransactionImplBase {

    private Reference reference;
	private Dispatcher dispatcher;
	private Logger logger;

	public TransactionImpl(Reference reference) {
		this.reference = reference;
		this.dispatcher = Dispatcher.init(reference.getIp(),
				reference.getPort());
		this.logger = Logger.getLogger("cashAccess");
	}

	@Override
    public void deposit(String accountID, double amount) throws InvalidParamException {
		logger.info("deposit: " + "accountID = " + accountID + ", amount = "
				+ amount);
		String methodName = "deposit";
		Class<?>[] types = new Class<?>[] { String.class, Double.TYPE };
		Object[] args = new Object[] { accountID, amount };
		ReturnMethod returnMethod = (ReturnMethod) dispatcher.sendToSkeleton(reference, methodName, types, args);
		Throwable exception = returnMethod.getThrowable();
		if (exception != null) {
			if (exception instanceof InvalidParamException) {
				throw (InvalidParamException) exception;
			}
		}
    }

    @Override
    public void withdraw(String accountID, double amount) throws InvalidParamException, OverdraftException {
		logger.info("withdraw: " + "accountID = " + accountID + ", amount = "
				+ amount);
		String methodName = "withdraw";
		Class<?>[] types = new Class<?>[] { String.class, Double.TYPE };
		Object[] args = new Object[] { accountID, amount };
		ReturnMethod returnMethod = (ReturnMethod) dispatcher.sendToSkeleton(reference, methodName, types, args);
		Throwable exception = returnMethod.getThrowable();
		if (exception != null) {
			if (exception instanceof InvalidParamException) {
				throw (InvalidParamException) exception;
			}
			if (exception instanceof OverdraftException) {
				throw (OverdraftException) exception;
			}
		}
    }

    @Override
    public double getBalance(String accountID) throws InvalidParamException {
		logger.info("getBalance: " + "accountID = " + accountID);
		String methodName = "getBalance";
		Class<?>[] types = new Class<?>[] { String.class };
		Object[] args = new Object[] { accountID};
		ReturnMethod returnMethod = (ReturnMethod) dispatcher.sendToSkeleton(reference, methodName, types, args);
		Throwable exception = returnMethod.getThrowable();
		if (exception != null) {
			if (exception instanceof InvalidParamException) {
				throw (InvalidParamException) exception;
			}
		}
		return (Double) returnMethod.getReturnValue();
    }
}
