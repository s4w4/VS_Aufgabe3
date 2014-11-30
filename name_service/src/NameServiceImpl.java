import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * NameServiceImpl
 */
public class NameServiceImpl implements NameService {

    /** Maximale Anzahl der eingehenden Anfragen */
    private final int SOCKET_QUEUE_LENGTH = 40;
    /** Timeout in Millisekunden */
    private final int TIMEOUT_IN_MS = 1000;

    /** Flag: gibt an ob Namensdienst beendet werden soll*/
    private boolean shutDown = false;

    /** Der Port vom Namensdienst*/
    private final int port;
    /** ThreadPool*/
    private final ExecutorService pool;

    /**
     * Erstellt ein NameService
     *
     * @param port              Port vom Namensdienst
     * @param threadPoolSize    maximale Anzahl von Threads
     * @return  NameService
     */
    public static NameService init(int port,int threadPoolSize){
        return new NameServiceImpl(port,threadPoolSize);
    }

    /**
     * Konstruktor
     *
     * @param port              Port vom Namensdienst
     * @param threadPoolSize    maximale Anzahl von Threads
     */
    private NameServiceImpl(int port,int threadPoolSize){
        this.port = port;
        this.pool = Executors.newFixedThreadPool(threadPoolSize,new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });
    }

    @Override
    public void run() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(this.port), SOCKET_QUEUE_LENGTH);
            serverSocketChannel.socket().setSoTimeout(TIMEOUT_IN_MS);
            while (!this.shutDown){
                pool.execute(RequestHandlerImpl.init(this, serverSocketChannel.socket().accept()));
            }
            serverSocketChannel.close();
        }catch (IOException e) {
            pool.shutdown();
        }
    }

    @Override
    public void rebind(String name, Reference reference) {

    }

    @Override
    public Reference resolve(String name) {
        return null;
    }

    @Override
    public void shutDown() {
        this.shutDown = true;
    }
}
