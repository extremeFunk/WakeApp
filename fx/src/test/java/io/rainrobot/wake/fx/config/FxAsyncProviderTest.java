package io.rainrobot.wake.fx.config;

import io.rainrobot.wake.client.ASyncProvider;
import io.rainrobot.wake.core.util.Log;
import io.rainrobot.wake.fx.JavaFXThreadingRule;
import javafx.application.Platform;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FxAsyncProviderTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    private ASyncProvider aSynd;
    private int var1 = 0;
    private int var2 = 0;

    @Before
    public void setUp() throws Exception {
        aSynd = new FxConfiguration(null).getAsyncProvider();
    }

    @Test
    public void simpleRun() throws Exception {
        Runnable run = () -> var1 = 1;
        Consumer<Exception> eHnadler = (e) -> var2 = 2;;
        Runnable doLast = () -> var2 = 1;

        aSynd.asyncCall(run, eHnadler, doLast);
        Thread.sleep(200);
        Platform.runLater(() -> {
            Assert.assertEquals(1, var1);
            Assert.assertEquals(1, var2);
        });
    }

    @Test
    public void throwExeption() throws Exception {
        Runnable run = () -> {
            throw new RuntimeException();
        };
        Consumer<Exception> eHandler = (e) -> var1 = 1;
        Runnable doLast = () -> var2 = 1;

        aSynd.asyncCall(run, eHandler, doLast);
        Thread.sleep(200);
        Platform.runLater(() -> {
            Assert.assertEquals(1, var2);
        });
        Assert.assertEquals(1, var1);
    }

    @Test
    public void supConTest() throws Exception {
        Supplier<Integer> sup = () -> 1;
        Consumer<Exception> eHnadler = (e) -> {};;
        Consumer<Integer> consumer = (i) -> var1 = i;
        Runnable doLast = () -> var2 = 1;

        aSynd.asyncCall(sup, consumer, eHnadler, doLast);
        Thread.sleep(200);
        Platform.runLater(() -> {
            Assert.assertEquals(1, var1);
            Assert.assertEquals(1, var2);
        });
    }

    @Test
    public void cancel() {

    }
}