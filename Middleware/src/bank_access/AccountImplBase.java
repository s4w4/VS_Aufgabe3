package bank_access;

import mware_lib.Reference;

/**
 * bank_access.AccountImplBase
 */
public abstract class AccountImplBase {

    public abstract void transfer(double amount) throws OverdraftException;

    public abstract double getBalance();

    public static AccountImplBase narrowCast(Object rawObjectRef) {
    	Reference reference = (Reference) rawObjectRef;
        return new AccountImpl(reference);
    }
}