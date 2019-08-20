package io.rainrobot.wake.fx.doa;

import io.rainrobot.wake.app.AppContainer;
import io.rainrobot.wake.fx.config.FxConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RMDoaTest {

    FxHibernateRMDoa rmDoa;

    @Before
    public void setup() {
        FxConfiguration config = new FxConfiguration(new AppContainer(), null);
        rmDoa = (FxHibernateRMDoa)config.getRememberMeDoa();
    }

    @Test
    public void rmDoaTest() throws Exception {
        rmDoa.set(true);
        Assert.assertTrue(rmDoa.isOn());
        rmDoa.set(false);
        Assert.assertFalse(rmDoa.isOn());
    }
}
