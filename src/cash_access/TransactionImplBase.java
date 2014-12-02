package cash_access;

import mware_lib.Reference;
import bank_access.ManagerImpl;

/**
 * cash_access.TransactionImplBase
 */
public abstract class TransactionImplBase {

    public abstract void deposit(String accountID, double amount)
            throws InvalidParamException;

    public abstract void withdraw(String accountID, double amount)
            throws InvalidParamException, OverdraftException;

    public abstract double getBalance(String accountID)
            throws InvalidParamException;

    public static TransactionImplBase narrowCast(Object rawObjectRef) {
    	Reference reference = (Reference) rawObjectRef;
        return new TransactionImpl(reference);
    }
}