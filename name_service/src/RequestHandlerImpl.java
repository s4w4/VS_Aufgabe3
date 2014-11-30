import java.net.Socket;

/**
 * RequestHandler
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

    }
}
