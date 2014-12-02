package name_service;

public enum Command {
	REQUEST_REBIND, 
	RESPONSE_REBIND, 
	REQUEST_RESOLVE, 
	RESPONSE_RESOLVE; 	
	
	
	public String handleInput(String inputWithOutCommand, NameService nameService){
		switch(this) {
		//Rebind Anfrage
			case REQUEST_REBIND:		
				String[] params = inputWithOutCommand.split(";"); 

				System.out.println("Request rebind");
				Reference reference = new Reference(params[2].trim(), params[0].trim(), Integer.parseInt(params[1].trim()), params[3].trim()); 
				nameService.rebind(params[3].trim(), reference); 
				return "response_rebind ! ok"; 
		//Response Anfrage
			case REQUEST_RESOLVE:
				System.out.println("Request resolve");
				
				Reference responseReference = nameService.resolve(inputWithOutCommand.trim()); 
				return createResponseRebindMessage(responseReference); 
			default: break;
		}
		return ""; 
	}
	
	
    /**
     * Erstellt die Rebind-Nachricht die zum Namensdienst geliefert wird
     * bsp:
     *  request_rebind ! 127.0.0.1;1234;Bank;MyBank
     * @param reference Reference
     * @return  String
     */
    private String createResponseRebindMessage(Reference reference){
    	if (reference == null)
    		return "response_resolve" +" ! "+"error";
        String host = reference.getIp();
        String port = reference.getPort()+"";
        String type = reference.getType();
        String name = reference.getName();
        return "response_resolve" +" ! "+host+";"+port+";"+type+";"+name;
    }
	
}
