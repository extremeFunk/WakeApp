package io.rainrobot.wake.client;

public class ResponseException extends IllegalStateException {
    private final int status;

    public ResponseException(int status, String msg) {
        super("status " + status + " " + msg);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
