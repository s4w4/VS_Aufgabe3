package name_service;

/**
 *  name_service.NameService
 */
public interface NameService extends Runnable{
	
   /**
    * ein Objekt (servant) anmelden.
    * Eine eventuell schon vorhandene Objektreferenz gleichen Namens
    * wird überschrieben.
    * 
    * @param name
    * @param reference
    */
	public void rebind(String name, Reference reference);
   /**
    *
    * Liefert eine generische Objektreferenz zu einem Namen.
    *
    * @param name  String
    * @return  Object
    */
    public Reference resolve(String name);

     public void shutDown();
}
