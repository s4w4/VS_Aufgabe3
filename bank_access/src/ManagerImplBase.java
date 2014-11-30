/**
 * ManagerImplBase
 */
public abstract class ManagerImplBase {

    public abstract String createAccount(String owner, String branch) throws InvalidParamException;

    public static ManagerImplBase narrowCast(Object rawObjectRef) {
        return null;
    }
}