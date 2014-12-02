package mware_lib;

/**
 * mware_lib.NameService
 * - Schnittstelle zum Namensdienstes -
 */
public abstract class NameService {

    /**
     * Meldet ein Objekt (servant) beim Namensdienst an.
     * Eine eventuell schon vorhandene Objektreferenz gleichen Namens
     * wird überschrieben.
     *
     * @param servant   Object
     * @param name      String
     */
    public abstract void rebind(Object servant, String name);

    /**
     *
     * Liefert eine generische Objektreferenz zu einem Namen.
     *
     * @param name  String
     * @return  Object
     */
    public abstract Object resolve(String name);

}