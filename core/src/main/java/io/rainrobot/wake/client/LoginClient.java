package io.rainrobot.wake.client;

import io.rainrobot.wake.core.LoginEntity;


public class LoginClient {

	private IHttpRequestSender sender;
	private final String URL;

	public LoginClient(IHttpRequestSender sender, String url) {
		this.sender = sender;
		URL = url;
	}

	public String login(LoginEntity login) {
		return sender.send(URL, HttpMethodEnum.POST, String.class, login);
	}
}
