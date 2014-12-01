package mware_lib;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * NameServiceImpl
 */
public class NameServiceImpl extends NameService {
    private final String REQUEST_REBIND_MESSAGE_COMMAND = "request_rebind";
    private final String RESPONSE_REBIND_MESSAGE_COMMAND = "response_rebind";
    private final String RESPONSE_REBIND_OK = "ok";
    private final String RESPONSE_REBIND_ERROR = "error";
    private final String REQUEST_RESOLVE_MESSAGE_COMMAND = "request_resolve";
    private final String RESPONSE_RESOLVE_MESSAGE_COMMAND = "response_resolve";

    private final int nsPort;
    private final String nsHost;
    private final ServerSocket socket;
    private Map<Reference, Object> servantMap = Collections.synchronizedMap(new HashMap<Reference, Object>());

    /**
     * Erstellt NameServiceImpl
     * @param nsHost    Host vom Namensdienst
     * @param nsPort    Port vom Namensdienst
     * @param socket    ServerSocket von der Application
     * @return  NameServiceImpl
     */
    public static NameServiceImpl init(String nsHost, int nsPort, ServerSocket socket){
        return new NameServiceImpl(nsHost, nsPort, socket);
    }

    /**
     * Konstruktor
     *
     * @param nsHost    Host vom Namensdienst
     * @param nsPort    Port vom Namensdienst
     * @param socket    ServerSocket von der Application
     */
    public NameServiceImpl(String nsHost, int nsPort, ServerSocket socket){
        this.nsHost = nsHost;
        this.nsPort = nsPort;
        this.socket = socket;
    }

    @Override
    public void rebind(Object servant, String name) {
        Reference reference = addServant(servant, name);
        String requestMessage = createRequestRebindMessage(reference);

        try {
            Socket nsSocket = new Socket(this.nsHost, this.nsPort);
            Connection connection = Connection.init(nsSocket);
            connection.send(requestMessage);
            checkResponseRebindMessage(connection.receive());

            connection.close();
        } catch (IOException e) {
            // TODO catch
        } catch (ResponseException e) {
            System.err.println(e);
        }
    }

    @Override
    public Object resolve(String name) {
        Reference reference = null;
        String requestMessage = createRequestResolveMessage(name);
        try {
            Socket nsSocket = new Socket(this.nsHost, this.nsPort);
            Connection connection = Connection.init(nsSocket);
            connection.send(requestMessage);
            reference = checkResponseResolveMessage(connection.receive());

        } catch (IOException e) {
            // TODO catch
        } catch (ResponseException e) {
            System.err.println(e);
        }
        return reference;
    }


    /**
     * Prüft Antwort vom Namensdienst bei Rebind
     *
     * @param response  Anwort vom Namensdienst
     * @throws ResponseException
     */
    private void checkResponseRebindMessage(String response) throws ResponseException {
        if (response.isEmpty()) throw new ResponseException("Leere Response vom Namensdienst bei Response_Rebind");

        String[] reponseArgs = response.split("!");
        if (reponseArgs == null) throw new ResponseException("Falsche Response vom Namensdienst bei Response_Rebind");

        if (!reponseArgs[0].equals(RESPONSE_REBIND_MESSAGE_COMMAND) &&
                reponseArgs[1].indexOf(RESPONSE_REBIND_OK) != 1){

            String errMessage = "";
            if (reponseArgs[1] == null)
                errMessage = "Keine Parameter";
            if (reponseArgs[1].indexOf(RESPONSE_REBIND_ERROR) == 1)
                errMessage = "Fehler beim Namensdienst";
            throw new ResponseException("ERROR RESPONSE REBIND: "+errMessage);
        }
    }

    /**
     * Prüft Antwort vom Namensdienst bei Resolve
     *
     * @param response  Anwort vom Namensdienst
     * @return Reference
     * @throws ResponseException
     */
    private Reference checkResponseResolveMessage(String response) throws ResponseException{
        Reference reference = null;

        if (response.isEmpty()) throw new ResponseException("Leere Response vom Namensdienst bei Response_Resolve");
        String[] reponseArgs = response.split("!");
        if (reponseArgs == null || response.indexOf(REQUEST_RESOLVE_MESSAGE_COMMAND) != 1)
            throw new ResponseException("Falsche Response vom Namensdienst bei Response_Resolve");
        if (reponseArgs.length == 5 ) {
            String host = reponseArgs[1];
            int port = Integer.parseInt(reponseArgs[2]);
            String type = reponseArgs[3];
            String name = reponseArgs[4];
            reference = new Reference(host,port,type,name);

        }else throw new ResponseException("Falsche Anzahl an Parameter vom Namensdienst bei Response_Resolve");

        return reference;
    }

    /**
     * Erstellt die Rebind-Nachricht die zum Namensdienst geliefert wird
     * bsp:
     *  request_rebind ! 127.0.0.1;1234;Bank;MyBank
     * @param reference Reference
     * @return  String
     */
    private String createRequestRebindMessage(Reference reference){
        String host = reference.getIp();
        String port = reference.getPort()+"";
        String type = reference.getType();
        String name = reference.getName();
        return REQUEST_REBIND_MESSAGE_COMMAND +" ! "+host+";"+port+";"+type+";"+name;
    }

    /**
     * Erstellt die Resolve-Nachricht die zum Namensdienst geliefert wird
     * bsp:
     *  request_resolve ! MyBank
     * @param name  String
     * @return String
     */
    private String createRequestResolveMessage(String name) {
        return REQUEST_RESOLVE_MESSAGE_COMMAND +" ! "+name;
    }


    /**
     * speichert ein Servant
     *
     * @param servant   Object
     * @param name      String
     * @return  Reference
     */
    private Reference addServant(Object servant, String name) {
        Reference reference = null;
        try {
            String localhost = InetAddress.getLocalHost().getHostName();
            int port = socket.getLocalPort();
            String type = servant.getClass().getName();
            reference = new Reference(localhost, port, type, name);
            servantMap.put(reference,servant);
        } catch (UnknownHostException e) {
            // TODO catch
        }
        return reference;
    }

    /**
     * Beendet den Namensdienst
     * @throws IOException
     */
    public void shutdown() throws IOException {
        this.socket.close();
    }
}
