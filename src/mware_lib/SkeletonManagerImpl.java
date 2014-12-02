package mware_lib;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.*;

/**
 * SkeletonManagerImpl
 */
public class SkeletonManagerImpl extends SkeletonManager {

    private final int THREAD_POOL_SIZE = 1000;
    private final ExecutorService pool;
    private boolean interrupt = false;

    private NameServiceImpl nameService;
    private Logger logger;

    protected SkeletonManagerImpl(NameServiceImpl nameService, Logger logger){
        this.nameService = nameService;
        this.pool = createThreadPool();
        this.logger = logger;
    }

    @Override
    public void shutdown() {
        logger.log(Level.INFO, "SkeletonManager beendet");
        this.setInterrupt(true);
    }

    @Override
    public void run() {
        logger.log(Level.CONFIG, "SkeletonManager gestartet");
        ServerSocket serverSocket = null;
        try {
             serverSocket = nameService.getSocket();
            while (!this.isInterrupt()){
                pool.execute(Skeleton.init(serverSocket.accept(),nameService, logger));
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE,e.toString());
        } finally {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE,e.toString());
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

    /**
     * liefert den Flag für den Interrupt
     * @return Boolean
     */
    public boolean isInterrupt() {
        return interrupt;
    }

    /**
     * setzt den Flag für den Interrupt
     * @param interrupt Boolean
     */
    public void setInterrupt(boolean interrupt) {
        this.interrupt = interrupt;
    }
}
