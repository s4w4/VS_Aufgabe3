package name_service;

public class Main {
	public static void main(String[] args) {
        if (args.length != 1) System.exit(0);

        try {
            int port = Integer.parseInt(args[0]);
            NameService ns = NameServiceImpl.init(port);
            Thread t = new Thread(ns);
            t.start();
        }catch (NumberFormatException nfe){ System.exit(0); }

	}
}