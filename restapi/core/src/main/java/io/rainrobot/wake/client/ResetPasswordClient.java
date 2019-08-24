package io.rainrobot.wake.client;

public class ResetPasswordClient {
    private IHttpRequestSender sender;
    private final String URL;

    public ResetPasswordClient(IHttpRequestSender sender, String url) {
        this.sender = sender;
        URL = url;
    }

    public void sendEmailRequest(String email) {
        sender.send(URL, HttpMethodEnum.POST, Void.class, email);
    }

    public void resetPassword(String token, String passowrd) {
        sender.sendWithParam(URL, token, HttpMethodEnum.POST, Void.class, passowrd);
    }
}
