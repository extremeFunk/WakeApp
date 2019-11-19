package io.rainrobot.wake.rest.dto;

import io.rainrobot.wake.rest.configuration.appuser.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Document
@Builder
@AllArgsConstructor
@EqualsAndHashCode()
@Getter
public class AppUser {

	@Id
	@NotEmpty
	private String username;

	@NotEmpty
	private String email;
	
	@NotEmpty
	private String password;

	@NotEmpty
	private List<GrantedAuthority> authority;

	@NotEmpty
	private State state;

	private Integer resetToken;

	private Account account;

	public void setResetToken(int resetToken) {
		this.resetToken = resetToken;
	}
}
