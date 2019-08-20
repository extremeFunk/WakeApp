package io.rainrobot.wake.core.util;

import java.util.function.Supplier;

public class Singleton <T>{
    T instance;

    public T get(Supplier<T> builder) {
        if (instance == null) {
            instance = builder.get();
             if (instance == null) {
                 throw new NullPointerException("Builder mustn't return null");
             }
        }
        return instance;
    }

    public T get() {
        return instance;
    }

    public void set(T instance) {
        if (instance != null) {
            throw new SingletonException("Trying to instantiate Singleton more then once");
        }
        else {
            this.instance = instance;
        }
    }
}
