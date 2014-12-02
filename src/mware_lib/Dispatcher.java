package mware_lib;

public class Dispatcher {

	private String host;
	private int port;

	public Dispatcher(String host, int port) {
		this.host = host; 
		this.port = port; 
	}

	public static Dispatcher init(String host, int port) {
		return new Dispatcher(host, port);
	}

	public Object sendToSkeleton(Reference reference, String methodName,
			Class<?>[] types, Object[] args) {
				return args;
		// TODO Auto-generated method stub
		
	}

}
