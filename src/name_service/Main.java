package name_service;

public class Main {
	public static void main(String[] args) {
		int port = 5000; 
		NameServiceImpl ns = NameService.init(port);
		ns.rebind("a",null);
	}
}
