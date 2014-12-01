package name_service;

/**
 * name_service.NameService
 */
public abstract class NameService{

	/**
	 * eine generische Objektreferenz anmelden. Eine eventuell schon vorhandene
	 * Objektreferenz gleichen Namens wird überschrieben.
	 * 
	 * @param name
	 * @param reference
	 */
	public abstract void rebind(String name, Reference reference);

	/**
	 *
	 * Liefert eine generische Objektreferenz zu einem Namen.
	 *
	 * @param name
	 * @return reference
	 */
	public abstract Reference resolve(String name);

	/**
	 * Nameserver stoppen 
	 */
	public abstract void shutDown();
	
	/**
	 * Nameserver wird erstellt und gestartet
	 * 
	 * @param port
	 * @return
	 */
	public static NameServiceImpl init(int port){
		return new NameServiceImpl(port); 
	}
}
