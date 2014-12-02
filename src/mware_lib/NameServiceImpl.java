package mware_lib;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;

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
    private Logger logger;

    /**
     * Erstellt NameServiceImpl
     * @param nsHost    Host vom Namensdienst
     * @param nsPort    Port vom Namensdienst
     * @param socket    ServerSocket von der Application
     * @return  NameServiceImpl
     */
    public static NameServiceImpl init(String nsHost, int nsPort, ServerSocket socket, Logger logger){
        return new NameServiceImpl(nsHost, nsPort, socket, logger);
    }

    /**
     * Konstruktor
     *
     * @param nsHost    Host vom Namensdienst
     * @param nsPort    Port vom Namensdienst
     * @param socket    ServerSocket von der Application
     */
    public NameServiceImpl(String nsHost, int nsPort, ServerSocket socket, Logger logger){
        this.nsHost = nsHost;
        this.nsPort = nsPort;
        this.socket = socket;
        this.logger = logger;
    }

    @Override
    public void rebind(Object servant, String name) {
        logger.log(Level.CONFIG, "rebind mit servant= "+servant+", name= "+name);
        Reference reference = addServant(servant, name);
        String requestMessage = createRequestRebindMessage(reference);

        try {
            Socket nsSocket = new Socket(this.nsHost, this.nsPort);
            ConnectionString connectionString = ConnectionString.init(nsSocket);
            connectionString.send(requestMessage);
            logger.log(Level.INFO, "Rebind Send: Nachricht= "+requestMessage);
            checkResponseRebindMessage(connectionString.receive());
            connectionString.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE,e.toString());
        } catch (ResponseException e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    @Override
    public Object resolve(String name) {
        logger.log(Level.CONFIG, "Resolve mit Name= "+name);
        Reference reference = null;
        String requestMessage = createRequestResolveMessage(name);
        try {
            Socket nsSocket = new Socket(this.nsHost, this.nsPort);
            ConnectionString connectionString = ConnectionString.init(nsSocket);
            connectionString.send(requestMessage);
            logger.log(Level.INFO, "Resolve Send: Nachricht= "+requestMessage);
            reference = checkResponseResolveMessage(connectionString.receive());

        } catch (IOException e) {
            logger.log(Level.SEVERE,e.toString());
        } catch (ResponseException e) {
            logger.log(Level.SEVERE,e.toString());
        }
        return reference;
    }

    /**
     * Beendet den Namensdienst
     * @throws IOException
     */
    public void shutdown() throws IOException {
        logger.log(Level.INFO,"NameService beendet");
        this.socket.close();
    }

    public Object findServant(Reference servant){
        Object obj = servantMap.get(servant);
        logger.log(Level.INFO,"findServant: servant= "+servant+", ReturnObj= "+obj);
        return obj;
    }


    /**
     * Prüft Antwort vom Namensdienst bei Rebind
     *
     * @param response  Anwort vom Namensdienst
     * @throws ResponseException
     */
    private void checkResponseRebindMessage(String response) throws ResponseException {
        logger.log(Level.INFO, "Rebind Receive: Nachricht= "+response);
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
        logger.log(Level.INFO, "Resolve Receive: Nachricht= "+response);
        Reference reference = null;
        if (response.isEmpty()) throw new ResponseException("Leere Response vom Namensdienst bei Response_Resolve");
        String[] reponseArgs = response.split("!");
        if (reponseArgs == null)
            throw new ResponseException("Falsche Response vom Namensdienst bei Response_Resolve");

        if (response.indexOf(RESPONSE_RESOLVE_MESSAGE_COMMAND) == 1 && reponseArgs.length >= 1 )
            throw new ResponseException("Error Response vom Namensdienst");

        if (reponseArgs.length == 2 ) {
            String[] reponseParams = reponseArgs[1].split(";");
            String host = reponseParams[0].trim();
            int port = Integer.parseInt(reponseParams[1].trim());
            String type = reponseParams[2].trim();
            String name = reponseParams[3].trim();
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
        String returnStr = REQUEST_REBIND_MESSAGE_COMMAND +" ! "+host+"; "+port+"; "+type+"; "+name;
        return returnStr;
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
        logger.log(Level.INFO, "AddServant: servant= "+servant+", name= "+name);
        Reference reference = null;
        try {
            String localhost = InetAddress.getLocalHost().getHostName();
            int port = socket.getLocalPort();
            String type = servant.getClass().getName();
            reference = new Reference(localhost, port, type, name);
            servantMap.put(reference,servant);

        } catch (UnknownHostException e) {
            logger.log(Level.SEVERE,e.toString());
        }
        return reference;
    }

    public ServerSocket getSocket() {
        return socket;
    }
}
