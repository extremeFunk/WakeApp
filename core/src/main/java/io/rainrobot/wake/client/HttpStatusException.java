package io.rainrobot.wake.client;

public class HttpStatusException extends RuntimeException {

    private final int statusCode;
    private final String statusText;

    public HttpStatusException(int statusCode, String statusText) {
        this.statusCode = statusCode;
        this.statusText = statusText;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }
}
