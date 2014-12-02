package test.mware_lib;
import junit.framework.Assert;
import junit.framework.TestCase;
import mware_lib.NameService;
import mware_lib.NameServiceImpl;
import mware_lib.Reference;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tcp_advanced.Connection;

import javax.lang.model.element.Name;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

public class MWareNameServiceImplTest extends TestCase {


    public MWareNameServiceImplTest(String name) throws IOException {
        super(name);
    }


    public NameServiceImpl createNameServiceImplDefault(int nsPort) throws IOException {
        String nsHost = "localhost";
        ServerSocket serverSocket = new ServerSocket(0);
        Logger logger = Logger.getLogger(MWareNameServiceImplTest.class.getName() );
        return NameServiceImpl.init(nsHost, nsPort,serverSocket, logger);
    }

    @Test
    public void testRebind() throws IOException {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TestServer theServer = new TestServer(14004);
                    Connection myConnection = theServer.getConnection();
                    System.out.println(myConnection.receive());
                    myConnection.send("response_rebind!ok");
                    myConnection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        })).start();

        NameServiceImpl nameService = createNameServiceImplDefault(14004);

        TestClass testClass = new TestClass();
        String localhost = InetAddress.getLocalHost().getHostName();
        int port = nameService.getSocket().getLocalPort();
        String type = testClass.getClass().getName();
        String name = "test";
        Reference reference = new Reference(localhost, port, type, name);

        nameService.rebind(testClass,name);
        Object obj = nameService.findServant(reference);

        System.out.println(obj);
    }

    @Test
    public void testResolve() throws IOException {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TestServer theServer = new TestServer(14003);
                    Connection myConnection = theServer.getConnection();
                    System.out.println(myConnection.receive());
                    myConnection.send("response_rebind!ok");
                    System.out.println(myConnection.receive());
                    myConnection.send("response_resolve!localhost;54371;test.mware_lib.MWareNameServiceImplTest$TestClass;test");
                    myConnection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        })).start();

        NameServiceImpl nameService = createNameServiceImplDefault(14003);

        TestClass testClass = new TestClass();
        String localhost = InetAddress.getLocalHost().getHostName();
        int port = nameService.getSocket().getLocalPort();
        String type = testClass.getClass().getName();
        String name = "test";
        Reference reference = new Reference(localhost, port, type, name);

        nameService.rebind(testClass,name);
        nameService.resolve(name);
        Object obj = nameService.findServant(reference);

        System.out.println(obj);
    }

    private void startServer(final String sendMSG, final int port) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TestServer theServer = new TestServer(port);
                    Connection myConnection = theServer.getConnection();
                    System.out.println(myConnection.receive());
                    myConnection.send(sendMSG);
                    myConnection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        })).start();
    }


    private class TestClass{
        private String theField;
    }

    private class TestServer{

        private ServerSocket MySvrSocket;

        public TestServer(int listenPort) throws IOException {
            MySvrSocket = new ServerSocket(listenPort);
        }

        public Connection getConnection() throws IOException {
            return new Connection(MySvrSocket.accept());
        }

    }
}
