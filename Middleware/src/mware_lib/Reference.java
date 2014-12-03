package mware_lib;

import java.io.Serializable;

/**
 * mware_lib.Reference
 */
public class Reference implements Serializable{

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reference reference = (Reference) o;

        if (port != reference.port) return false;
        if (ip != null ? !ip.equals(reference.ip) : reference.ip != null) return false;
        if (name != null ? !name.equals(reference.name) : reference.name != null) return false;
        if (type != null ? !type.equals(reference.type) : reference.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ip != null ? ip.hashCode() : 0;
        result = 31 * result + port;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
