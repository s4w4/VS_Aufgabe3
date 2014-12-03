package mware_lib;

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
    public static Skeleton init(Socket socket, NameServiceImpl nameService, Logger logger){ return new SkeletonImpl(socket, nameService, logger);}
}
