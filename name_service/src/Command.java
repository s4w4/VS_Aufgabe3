import java.util.Map;

/**
 * Command
 */
public enum Command {
    REQUEST_REBIND,
    RESPONSE_REBIND,
    REQUEST_RESOLVE,
    RESPONSE_RESOLVE;

    private String command;

    private Command(){
        this.command = this.toString();
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommand(Object arg) {
        return getCommand() + " " + arg.toString();
    }

    public String getInputParameter(String input) {
        if (input.length() > this.getCommand().length() + 1) {
            return input.substring(this.getCommand().length() + 1,
                    input.length());
        }
        return "";
    }

    public boolean isCommand(String input) {
        if (input != null && input.toUpperCase().startsWith(getCommand())) {
            return true;
        }
        return false;
    }

    public String handleInput(String inputWithCommand, RequestHandler requestHandler) {
        String inputParameter = getInputParameter(inputWithCommand);

        switch (this) {
            case REQUEST_REBIND:
                requestRebind(inputParameter);
            case RESPONSE_REBIND:
            case REQUEST_RESOLVE:
            case RESPONSE_RESOLVE:

            default:
                break;
        }
        return "";
    }

    private void requestRebind(String inputParameter) {
        String host = "";
        int port = 0;
        Object type = null;
        String name = "";


    }



}
