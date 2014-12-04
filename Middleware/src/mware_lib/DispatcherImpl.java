package mware_lib;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * DispatcherImpl
 */
public class DispatcherImpl extends Dispatcher{

    private String host;
    private int port;
    private Logger logger;

    public DispatcherImpl(String host, int port, Logger logger2) {
        this.host = host;
        this.port = port;

        /*
        try {
            fileHandler = new FileHandler("Dispatcher.log");
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter(simpleFormatter);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        logger = Logger.getLogger(DispatcherImpl.class.getName() );
        //logger.addHandler(fileHandler);
        logger.setLevel(Level.OFF);
        logger.log(Level.INFO,"Logger erstellt");

    }

    public Object sendToSkeleton(Reference reference, String methodName,
                                 Class<?>[] types, Object[] args)  {
        logger.log(Level.INFO,"Disp: reference= "+reference+", methodName= "+methodName+", types= "+types+", args= "+args);
        ReturnMethod returnMethod = null;
        CallMethod callMethod = new CallMethod(reference, methodName, args, types);
        logger.log(Level.INFO,"CALLMETHODE: " + callMethod+" host= "+host+" port= "+port);
        Socket socket = null;
        try {
            socket = new Socket(host, port);
            logger.log(Level.INFO,"Socket= "+socket);
            ConnectionObject connectionObject = ConnectionObject.init(socket);
            logger.log(Level.INFO,"Con = "+connectionObject);

            connectionObject.send(callMethod);
            logger.log(Level.INFO,"CallMethod gesendet");
            returnMethod = (ReturnMethod) connectionObject.receive();
            logger.log(Level.INFO,"ReturnMethod empfangen: "+returnMethod.getReturnValue()+", "+returnMethod.getThrowable());

            connectionObject.close();
        } catch (IOException e) {
            logger.log(Level.INFO, e.toString());
        } catch (ClassNotFoundException e) {
            logger.log(Level.INFO, e.toString());
        } finally {
            try {
               if (socket != null) {
                 socket.close();
               }
            } catch (IOException e) {
                logger.log(Level.INFO, e.toString());
            }
        }

        return returnMethod;
    }
}
