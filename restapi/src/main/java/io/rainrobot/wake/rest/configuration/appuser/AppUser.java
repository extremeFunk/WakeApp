package io.rainrobot.wake.rest.configuration.appuser;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Document
public class AppUser {

	@Id
	int id;

	@NotEmpty
	private String username;

	@NotEmpty
	private String email;
	
	@NotEmpty
	private String password;
		
	@NotEmpty
	private String authority;

	@NotEmpty
	private String state;

	private Integer resetToken;


	public AppUser(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public AppUser() {}

	public String getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state.toString();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	//TODO
	public List<GrantedAuthority> getAuthority() {
		List<GrantedAuthority> rtn = new ArrayList<>();
		rtn.add(new SimpleGrantedAuthority(authority));
		return rtn;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority.getType();
	}

	public String getEmail() {
		return email;
	}

	public AppUser setEmail(String email) {
		this.email = email;
		return this;
	}

	public void setResetToken(Integer resetToken) {
		this.resetToken = resetToken;
	}

	public Integer getResetToken() {
		return resetToken;
	}

	@Override
	public String toString() {
		return "User [username=" + username +
				", password=" + password +
				", authority=" + authority +
				", state=" + state +
				"]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AppUser))
			return false;
		AppUser other = (AppUser) obj;
		if (username != other.username)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
