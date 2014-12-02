package mware_lib;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * SkeletonImpl.
 */
public class SkeletonImpl extends Skeleton {

    private final Socket socket;
    private final NameServiceImpl nameService;

    protected SkeletonImpl(Socket socket, NameServiceImpl nameService){
        this.socket = socket;
        this.nameService = nameService;
    }

    @Override
    public void run() {
        try {

            ConnectionObject connectionObject = ConnectionObject.init(socket);
            CallMethod callMethod = receiveMethod(connectionObject);
            ReturnMethod returnMethod = invoke(callMethod);
            connectionObject.send(returnMethod);

        } catch (IOException e) {
            // TODO catch
        } catch (ClassNotFoundException e) {
            // TODO catch
        } catch (RuntimeException e) {
            // TODO catch
        }
    }

    private CallMethod receiveMethod(ConnectionObject connectionObject) throws IOException, ClassNotFoundException,RuntimeException {
        return (CallMethod) connectionObject.receive();
    }

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
        } catch (InvocationTargetException e) {
            throwable = e.getCause();
        } catch (NoSuchMethodException e) {
            throwable = e.getCause();
        } catch (IllegalAccessException e) {
            throwable = e.getCause();
        } finally {
            returnMethod = new ReturnMethod(returnValue, throwable);
        }
        return returnMethod;
    }

}
