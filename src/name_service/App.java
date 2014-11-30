package name_service;

/**
 * Created by Alex on 30.11.2014.
 */
public class App {

    public static void main(String[] args){
        Command command = Command.REQUEST_REBIND;
        String nc = command.getCommand(" ! localhost; 5000; double; value");
        command.handleInput(nc,null);
    }
}
