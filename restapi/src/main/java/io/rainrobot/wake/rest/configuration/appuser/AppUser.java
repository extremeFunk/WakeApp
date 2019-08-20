package io.rainrobot.wake.rest.configuration.appuser;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Table(name="User")
public class AppUser {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	
	@NotEmpty
	@Column(name="USERNAME", nullable=false)	
	private String username;

	@NotEmpty
	@Column(name="EMAIL")
	private String email;
	
	@NotEmpty
	@Column(name="PASSWORD", nullable=false)
	private String password;
		
	@NotEmpty
	@Column(name="AUTHORITY", nullable=false)
	private String authority;

	@NotEmpty
	@Column(name="STATE", nullable=false)
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
