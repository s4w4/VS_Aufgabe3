package mware_lib;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Skeleton
 */
public abstract class Skeleton extends Thread{
    /**
     * erstellt ein Skeleton
     * @param socket        Socket
     * @param nameService   NameServiceImpl
     * @return  Skeleton
     */
    public static Skeleton init(Socket socket, ServerSocket socketServer, NameServiceImpl nameService, Logger logger){ return new SkeletonImpl(socket,socketServer, nameService, logger);}
}
