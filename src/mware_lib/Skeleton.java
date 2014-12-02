package mware_lib;

import java.net.Socket;

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
    public static Skeleton init(Socket socket, NameServiceImpl nameService){ return new SkeletonImpl(socket, nameService);}
}
