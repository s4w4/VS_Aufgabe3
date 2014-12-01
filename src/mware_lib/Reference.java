package mware_lib;

/**
 * mware_lib.Reference
 */
public class Reference {

    private String ip;
    private int port;
    private String type;
    private String name;

    public Reference(String ip, int port, String type, String name){
        this.ip = ip;
        this.port = port;
        this.type = type;
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }
}
