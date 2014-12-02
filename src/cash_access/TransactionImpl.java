package cash_access;

import java.util.logging.Logger;

import mware_lib.Dispatcher;
import mware_lib.Reference;

/**
 * cash_access.TransactionImpl
 */
public class TransactionImpl extends TransactionImplBase {

    private Reference reference;
	private Object dispatcher;
	private Logger logger;

	public TransactionImpl(Reference reference) {
		this.reference = reference;
		this.dispatcher = Dispatcher.init(reference.getIp(),
				reference.getPort());
		this.logger = Logger.getLogger("cashAccess");
	}

	@Override
    public void deposit(String accountID, double amount) throws InvalidParamException {

    }

    @Override
    public void withdraw(String accountID, double amount) throws InvalidParamException, OverdraftException {

    }

    @Override
    public double getBalance(String accountID) throws InvalidParamException {
        return 0;
    }
}
