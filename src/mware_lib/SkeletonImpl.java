package mware_lib;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.logging.*;

/**
 * SkeletonImpl.
 */
public class SkeletonImpl extends Skeleton {

    private final Socket socket;
    private final NameServiceImpl nameService;
    private Logger logger;

    protected SkeletonImpl(Socket socket, NameServiceImpl nameService, Logger logger){
        this.socket = socket;
        this.nameService = nameService;
        this.logger = logger;
    }

    @Override
    public void run() {
        try {

            ConnectionObject connectionObject = ConnectionObject.init(socket);
            CallMethod callMethod = receiveMethod(connectionObject);
            ReturnMethod returnMethod = invoke(callMethod);
            connectionObject.send(returnMethod);

        } catch (IOException e) {
            logger.log(Level.SEVERE,e.toString());
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,e.toString());
        } catch (RuntimeException e) {
            logger.log(Level.SEVERE,e.toString());
        }
    }

    private CallMethod receiveMethod(ConnectionObject connectionObject) throws IOException, ClassNotFoundException,RuntimeException {
        return (CallMethod) connectionObject.receive();
    }

    /**
     * Ruft die originale Methode auf und liefert den Returnwert zurück
     * @param callMethod    CallMethod
     * @return  ReturnMethod
     */
    private ReturnMethod invoke(CallMethod callMethod)   {
        ReturnMethod returnMethod;
        Object returnValue = null;
        Throwable throwable = null;

        Reference reference = callMethod.getReference();
        String methodName = callMethod.getMethodName();
        Object[] args = callMethod.getArgs();
        Class<?>[] types = callMethod.getTypes();
        Object servant = nameService.findServant(reference);

        try {
            Class<?> aClass = Class.forName(reference.getType());
            Method method = aClass.getMethod(methodName, types);
            method.setAccessible(true);
            returnValue = method.invoke(servant, args);
        } catch (ClassNotFoundException e) {
            throwable = e.getCause();
            logger.log(Level.SEVERE,e.toString());
        } catch (InvocationTargetException e) {
            throwable = e.getCause();
            logger.log(Level.SEVERE,e.toString());
        } catch (NoSuchMethodException e) {
            throwable = e.getCause();
            logger.log(Level.SEVERE,e.toString());
        } catch (IllegalAccessException e) {
            throwable = e.getCause();
            logger.log(Level.SEVERE,e.toString());
        } finally {
            returnMethod = new ReturnMethod(returnValue, throwable);
        }
        return returnMethod;
    }

}
