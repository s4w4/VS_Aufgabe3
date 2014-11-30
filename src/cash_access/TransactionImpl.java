package cash_access;

/**
 * cash_access.TransactionImpl
 */
public class TransactionImpl extends TransactionImplBase {

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
