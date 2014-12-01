package name_service;

/**
 * name_service.Reference
 */
public class Reference {
	
	// Der Datentyp des referenzierten Objekts
	private String type;
	// Der IP, wo sich das Objekt befindet
	private String ip; 
	// Der Port, wo sich das Objekt befindet
	private int port; 
	// Entwurf: Der Kassenname des referenzierten Objekts
	// Änderung: Name des generisches Objektes
	private String name;
	
	public Reference(String type, String ip, int port, String name) {
		super();
		this.type = type;
		this.ip = ip;
		this.port = port;
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	} 
	
	
}
