package mware_lib;

import java.util.logging.Logger;

/**
 * SkeletonManager
 */
public abstract class SkeletonManager implements Runnable{

    public abstract void shutdown();

    public static SkeletonManagerImpl init(NameServiceImpl nameService, Logger logger){
        return new SkeletonManagerImpl(nameService, logger);
    }
}
