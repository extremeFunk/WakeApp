package io.rainrobot.wake.rest.dto;

public class SignUpForm {
	
	private String username;

	private String email;
	
	private String password;
	
	private String passwordConfirm;
	
	private String deviceName;

	public SignUpForm() {

	}

	public SignUpForm(String username, String password, String passwordConfirm, String email) {
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}


	public String getEmail() {
		return email;
	}

	public SignUpForm setEmail(String email) {
		this.email = email;
		return this;
	}
}
