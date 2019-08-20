package io.rainrobot.wake.rest.configuration.appuser;

public enum Authority {
	USER("ROLE_USER"),
	DBA("ROLE_DBA"),
	ADMIN("ROLE_ADMIN");
	
	String type;
	
	private Authority(String type){
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
	
	
}
