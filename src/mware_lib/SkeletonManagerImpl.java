package mware_lib;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * SkeletonManagerImpl
 */
public class SkeletonManagerImpl extends SkeletonManager {

    private final int SOCKET_TIMEOUT_IN_MS = 1000;
    private final int MAX_SOCKET_QUEUE_LENGTH = 40;
    private final int THREAD_POOL_SIZE = 1000;
    private final ExecutorService pool;
    private boolean interrupt = false;

    private NameServiceImpl nameService;

    protected SkeletonManagerImpl(NameServiceImpl nameService){
        this.nameService = nameService;
        this.pool = createThreadPool();
    }

    @Override
    public void shutdown() {
        this.setInterrupt(true);
    }

    @Override
    public void run() {
        ServerSocketChannel serverSocketChannel = null;
        try {
            int localPort = nameService.getLocalPort();
            serverSocketChannel = ServerSocketChannel.open();
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(localPort), MAX_SOCKET_QUEUE_LENGTH);

            while (!this.isInterrupt()){
                pool.execute(Skeleton.init(serverSocket.accept(),nameService));
            }

        } catch (IOException e) {
            // TODO catch
        } finally {
            try {
                if (serverSocketChannel != null) serverSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Erstellt einen ThreadPool für die Skeletons
     * @return ExecutorService
     */
    private ExecutorService createThreadPool() {
        return Executors.newFixedThreadPool(THREAD_POOL_SIZE, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });
    }


    public boolean isInterrupt() {
        return interrupt;
    }

    public void setInterrupt(boolean interrupt) {
        this.interrupt = interrupt;
    }
}
