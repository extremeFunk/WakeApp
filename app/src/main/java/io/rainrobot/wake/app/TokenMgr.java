package io.rainrobot.wake.app;

import io.rainrobot.wake.client.TestTokenClient;
import io.rainrobot.wake.client.IHttpRequestSender;

public class TokenMgr {

	private String token;

	private ITokenDoa tokenDoa;
	private IHttpRequestSender sender;
	private TestTokenClient testClient;

	public TokenMgr(ITokenDoa tokenDoa,
					IHttpRequestSender sender,
					TestTokenClient testClient) {
		this.tokenDoa = tokenDoa;
		this.sender = sender;
		this.testClient = testClient;
	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		tokenDoa.save(token);
		sender.setAuthorization(token);
	}

	public void setFromDoa() {
		this.token = tokenDoa.get();
		sender.setAuthorization(token);
		
	}
	
	public void removeToken() {
		tokenDoa.save("");
		this.token = null;
		sender.removeAuth();
	}

	public Boolean testToken(String username) {
		if(token == null || token == "") { setFromDoa(); }
		return testClient.testToken(username);
	}

	
}
