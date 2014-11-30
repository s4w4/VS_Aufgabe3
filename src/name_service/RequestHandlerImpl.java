package name_service;

import java.io.IOException;
import java.net.Socket;

/**
 * name_service.RequestHandler
 */
public class RequestHandlerImpl implements RequestHandler{

    private final NameServiceImpl nameService;
    private final Socket socket;

    public static RequestHandlerImpl init(NameServiceImpl nameService, Socket socket) {
        return new RequestHandlerImpl(nameService, socket);
    }

    private RequestHandlerImpl(NameServiceImpl nameService, Socket socket){
        this.nameService = nameService;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Connection connection = getConnection();
            String commandRequest = receive(connection);
        } catch (IOException e) {
            //TODO catch
        }
    }

    private String receive(Connection connection) throws IOException {
        String request = connection.receive();
        return request;
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
             connection = Connection.init(socket);
        } catch (IOException e) {
            // TODO catch
        }
        return connection;
    }
}
