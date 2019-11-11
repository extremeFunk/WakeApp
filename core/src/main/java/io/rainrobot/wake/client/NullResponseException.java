package io.rainrobot.wake.client;

public class NullResponseException extends RuntimeException{
    public NullResponseException() {
        super("response body was expected");
    }
}
