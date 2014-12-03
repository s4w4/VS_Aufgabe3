package name_service;

/**
 * name_service.NameService
 * 
 * Im Nameservice werden Referenzen zu einem Namen abgelegt/abgefragt
 * 
 */
public abstract class NameService implements Runnable {

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

}
