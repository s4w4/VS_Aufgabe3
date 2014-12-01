package mware_lib;

import java.net.Socket;

/**
 * Skeleton
 */
public abstract class Skeleton extends Thread{
    public static Skeleton init(Socket socket, NameServiceImpl nameService){ return new SkeletonImpl(socket, nameService);}
}
