package mware_lib;

/**
 * SkeletonManager
 */
public abstract class SkeletonManager implements Runnable{

    public abstract void shutdown();

    public static SkeletonManagerImpl init(NameServiceImpl nameService){
        return new SkeletonManagerImpl(nameService);
    }
}
