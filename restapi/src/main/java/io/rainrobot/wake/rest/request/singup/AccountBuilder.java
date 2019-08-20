package io.rainrobot.wake.rest.request.singup;

import java.util.Date;

import io.rainrobot.wake.core.Account;

public class AccountBuilder {
	
	public static Account build(String username) {
		
		Account account = new Account();
		
		account.setUsername(username);
		account.setLastChange(new Date());
		
		return account;
	}
}
