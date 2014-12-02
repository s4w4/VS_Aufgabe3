package test.mware_lib;

import junit.framework.Assert;
import junit.framework.TestCase;
import mware_lib.NameService;
import mware_lib.ObjectBroker;
import org.junit.Test;
import testSockets.FieldAccessible;

public class ObjectBrokerTest extends TestCase {

    private ObjectBroker objectBroker;

    public ObjectBrokerTest(String name) {
        super(name);
        createObjectBrokerDefault();
    }

    public void createObjectBrokerDefault() {
        String localHost = "localhost";
        int port = 4000;
        boolean debug = false;

        objectBroker = ObjectBroker.init(localHost, port, debug);
    }

    @Test
    public void testNameServiceNotNull(){
        NameService nameService = objectBroker.getNameService();
        Assert.assertNotNull(nameService);
    }

    @Test
    public void testRebind(){
        NameService nameService = objectBroker.getNameService();

        TestClass testClass = new TestClass();
        nameService.rebind(testClass, "testClass");
    }

    @Test
    public void testResolve(){
        NameService nameService = objectBroker.getNameService();

        TestClass testClass = new TestClass();
        nameService.rebind(testClass, "testClass");
        Object servant = nameService.resolve("testClass");


    }


    private class TestClass{
        private String theField;
    }
}
