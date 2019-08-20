package io.rainrobot.wake.client;

import io.rainrobot.wake.core.SignUpForm;
import io.rainrobot.wake.core.SingupError;

public class SignupClient {

	private IHttpRequestSender sender;
	private final String URL;

	public SignupClient(IHttpRequestSender sender, String url) {
		this.sender = sender;
		URL = url;
	}

	public SingupError[] register(SignUpForm form) {
		return sender.send(URL, HttpMethodEnum.POST, SingupError[].class, form);
	}
}
