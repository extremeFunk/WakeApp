package io.rainrobot.wake.app;

import org.junit.Before;
import org.junit.Test;

import io.rainrobot.wake.InstTestTamplet;
import io.rainrobot.wake.android.configuration.AndroidRememberMeDoa;

import static org.junit.Assert.*;

public class AndroidRememberMeDoaTest extends InstTestTamplet {

    private AndroidRememberMeDoa doa;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        doa = new AndroidRememberMeDoa(super.sharedPreferences);
    }


    @Test
    public void overallRMeDoaTest() {
        super.sharedPreferences.edit().clear();
        assertFalse(doa.isOn());

        super.sharedPreferences.edit().clear();
        doa.set(true);
        assertTrue(doa.isOn());

        super.sharedPreferences.edit().clear();
        doa.set(false);
        assertFalse(doa.isOn());
    }
}