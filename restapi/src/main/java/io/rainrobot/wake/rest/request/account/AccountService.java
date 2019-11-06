package io.rainrobot.wake.rest.request.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.rainrobot.wake.rest.dto.Account;

@Service
public class AccountService {

	@Autowired
	AccountDoa doa;
	
	public Account findByUsername(String username) {
		return doa.findByUsername(username);
	}

	public Account save(Account account) {
		return doa.save(account);
	}

	public void deleteByUsername(String username) {
		doa.deleteByUsername(username);
	}

}
