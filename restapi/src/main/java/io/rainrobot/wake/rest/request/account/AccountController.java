package io.rainrobot.wake.rest.request.account;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.rainrobot.wake.rest.dto.Path;
import io.rainrobot.wake.rest.configuration.appuser.AppAuth;
import io.rainrobot.wake.rest.dto.Account;

@RestController(Path.ACCOUNT)
public class AccountController {
	
	@Autowired
	private AccountService service;
	
	@GetMapping(Path.ACCOUNT)
	public Account getAccount(@AuthenticationPrincipal AppAuth user) {	
		return service.findByUsername(user.getName());
	}



	@DeleteMapping(Path.ACCOUNT)
	public void deleteAccount(@AuthenticationPrincipal AppAuth user) {
		service.deleteByUsername(user.getName());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = Path.USERNAME)
	public String getAccountName(@AuthenticationPrincipal AppAuth user) {
		return user.getName();
	}

	@RequestMapping(method = RequestMethod.GET, value = Path.LASTCHANGE)
	public Date getLastChange(@AuthenticationPrincipal AppAuth user) {
		return null;
	}
}
