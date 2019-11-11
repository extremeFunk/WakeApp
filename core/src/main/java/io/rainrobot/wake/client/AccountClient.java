package io.rainrobot.wake.client;

import java.util.Date;

import io.rainrobot.wake.core.Account;
import io.rainrobot.wake.core.Path;

public class AccountClient {

	private IHttpRequestSender sender;
	private final String URL;

	public AccountClient(IHttpRequestSender sender, String url) {
		URL = url;
		this.sender = sender;
	}

	public Account getAccount() {
		return sender.get(URL, Account.class);
	}
	
	public void updateAccount(Account account) {
		sender.send(URL, HttpMethodEnum.PUT, Void.class, account);
	}
	
	public void deleteAccount() {
		sender.send(URL, HttpMethodEnum.DELETE, Void.class, null);
	}

	public String getUsername() {
		return sender.get(Path.GLOBAL_URL_PREFIX + Path.USERNAME ,String.class);
	}

    public Date getLastChange() {
    	return sender.get(Path.GLOBAL_URL_PREFIX + Path.LASTCHANGE, Date.class);
    }

}
