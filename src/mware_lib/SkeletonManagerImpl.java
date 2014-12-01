package mware_lib;

import java.net.ServerSocket;

/**
 * SkeletonManagerImpl
 */
public class SkeletonManagerImpl implements SkeletonManager {

    private NameServiceImpl nameService;

    public static SkeletonManager init(NameServiceImpl nameService){
        return new SkeletonManagerImpl(nameService);
    }

    private SkeletonManagerImpl(NameServiceImpl nameService){
        this.nameService = nameService;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void run() {

    }
}
