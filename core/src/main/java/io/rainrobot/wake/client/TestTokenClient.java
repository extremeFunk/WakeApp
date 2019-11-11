package io.rainrobot.wake.client;

import io.rainrobot.wake.core.util.Log;

public class TestTokenClient {

	private IHttpRequestSender sender;
	private final String url;

	public TestTokenClient(IHttpRequestSender sender, String url) {
		this.sender = sender;
		this.url = url;
	}

	public Boolean testToken(String username) {
		 try {
			 sender.getWithParam(url, username, Void.class);
		 }	catch (HttpStatusException e) {
		 	if (e.getStatusCode() != 200) {
				Log.d(this, "test token failed, status: "
						+ e.getStatusCode()
						+ " msg: "
						+ e.getStatusText());
		 		return false;
			}
		 }
		 return true;
	}
}
