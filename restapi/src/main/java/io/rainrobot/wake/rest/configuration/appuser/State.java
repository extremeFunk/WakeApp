package io.rainrobot.wake.rest.configuration.appuser;

public enum State {

	ACTIVE("Active"),
	INACTIVE("Inactive"),
	DELETED("Deleted"),
	LOCKED("Locked");
	
	private String state;
	
	private State(final String state){
		this.state = state;
	}

	@Override
	public String toString(){
		return this.state;
	}
}
