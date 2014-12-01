package mware_lib;

import java.io.IOException;
import java.net.Socket;

/**
 * SkeletonImpl.
 */
public class SkeletonImpl extends Skeleton {

    private final Socket socket;
    private final NameServiceImpl nameService;

    protected SkeletonImpl(Socket socket, NameServiceImpl nameService){
        this.socket = socket;
        this.nameService = nameService;
    }

    @Override
    public void run() {
        try {
            ConnectionString connectionString = ConnectionString.init(socket);
            receiveMethod(connectionString);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveMethod(ConnectionString connectionString) {

    }
}
