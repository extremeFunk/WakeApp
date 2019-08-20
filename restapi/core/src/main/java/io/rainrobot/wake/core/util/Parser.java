package io.rainrobot.wake.core.util;

public abstract class Parser <T> {
    private final Class<T> tClass;

    public Parser (Class<T> tClass) {
        this.tClass = tClass;
    }
    public abstract String pars(T object);

    public Class<T> getType() {
        return tClass;
    }
}
