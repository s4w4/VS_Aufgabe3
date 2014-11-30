package name_service;

/**
 *  name_service.NameService
 */
public interface NameService extends Runnable{
    public void rebind(String name, Reference reference);

    public Reference resolve(String name);

     public void shutDown();
}
