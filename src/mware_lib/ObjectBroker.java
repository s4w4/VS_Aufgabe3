package mware_lib;


import java.io.IOException;
import java.net.ServerSocket;

/**
 * mware_lib.ObjectBroker
 *
 * - Front-End der Middleware -
 */
public class ObjectBroker {

    private SkeletonManager skeletonManager;
    private NameServiceImpl nameService;
    private ServerSocket serverSocket;

    /**
     * Erstellt einen mware_lib.ObjectBroker
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
     * @return  mware_lib.ObjectBroker
     */
    public static ObjectBroker init(String serviceHost, int listenPort, boolean debug)   {
        return new ObjectBroker(serviceHost, listenPort, debug);
    }

    /**
     * Liefert den Namensdienst (Stellvetreterobjekt).
     * @return mware_lib.NameService
     */
    public NameService getNameService() {
        return this.nameService;
    }

    /**
     * Beendet die Benutzung der Middleware in dieser Anwendung.
     */
    public void shutdown() {
        try {
            nameService.shutdown();
            skeletonManager.shutdown();

        } catch (IOException e) {
            // TODO: catch
        }
    }

    /**
     * Konstruktor
     *
     * @param serviceHost   Host vom Namensdienst
     * @param listenPort    Port vom Namensdienst
     * @param debug         Flag aktiviert Testausgaben
     */
    public ObjectBroker(String serviceHost, int listenPort, boolean debug) {
        try {
            serverSocket = new ServerSocket(0);
            this.nameService = NameServiceImpl.init(serviceHost, listenPort, serverSocket);
            skeletonManager = SkeletonManagerImpl.init(nameService);
            (new Thread(skeletonManager)).start();

        } catch (IOException e) {
            // TODO catch
        }
    }

}
