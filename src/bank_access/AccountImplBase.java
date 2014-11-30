package bank_access;

/**
 * bank_access.AccountImplBase
 */
public abstract class AccountImplBase {

    public abstract void transfer(double amount) throws OverdraftException;

    public abstract double getBalance();

    public static AccountImplBase narrowCast(Object rawObjectRef) {
        return null;
    }
}