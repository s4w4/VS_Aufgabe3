/**
 * ObjectBroker
 *
 * - Front-End der Middleware -
 */
public class ObjectBroker {

    /**
     * Erstellt einen ObjectBroker
     *
     * Das hier zurückgelieferte Objekt ist der zentrale Einstiegspunkt
     * der Middleware aus Anwendersicht sein.
     * Parameter: Host und Port, bei dem die Dienste (Namensdienst)
     * kontaktiert werden sollen. Mit debug sollen Testausgaben
     * der Middleware ein- oder ausgeschaltet werden können.
     *
     * @param serviceHost   Host vom Namensdienst
     * @param listenPort    Port vom Namensdienst
     * @param debug         Flag aktiviert Testausgaben
     * @return  ObjectBroker
     */
    public static ObjectBroker init(String serviceHost, int listenPort, boolean debug) {
        return new ObjectBroker(serviceHost, listenPort, debug);
    }

    /**
     * Liefert den Namensdienst (Stellvetreterobjekt).
     * @return NameService
     */
    public NameService getNameService() {
        //TODO: ObjectBroker.getNameService():
        return null;
    }

    /**
     * Beendet die Benutzung der Middleware in dieser Anwendung.
     */
    public void shutDown() {
        // TODO: ObjectBroker.shutDown():
    }

    /**
     * Konstruktor
     *
     * @param serviceHost   Host vom Namensdienst
     * @param listenPort    Port vom Namensdienst
     * @param debug         Flag aktiviert Testausgaben
     */
    public ObjectBroker(String serviceHost, int listenPort, boolean debug) {
        // TODO ObjectBroker Konstruktor
    }

}
