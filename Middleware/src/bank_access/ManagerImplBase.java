package bank_access;

import mware_lib.Reference;

/**
 * bank_access.ManagerImplBase
 */
public abstract class ManagerImplBase {

    public abstract String createAccount(String owner, String branch) throws InvalidParamException;

    public static ManagerImplBase narrowCast(Object rawObjectRef) {
    	Reference reference = (Reference) rawObjectRef;
        return new ManagerImpl(reference);
    }
}