package name_service;

/**
 * Created by Alex on 30.11.2014.
 */
public class NameServiceCreator {
    private static final int DEFAULT_PORT = 5000;
    private static final int DEFAULT_THREAD_POOL_SIZE = 1000;

    public static void main(String[] args){

        int port = DEFAULT_PORT;
        int threadPoolSize = DEFAULT_THREAD_POOL_SIZE;

        new Thread(NameServiceImpl.init(port,threadPoolSize)).start();
    }
}
