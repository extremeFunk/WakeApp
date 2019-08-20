package io.rainrobot.wake.rest;

import junit.framework.Assert;

import org.junit.Test;

import io.rainrobot.wake.InstTestTamplet;
import io.rainrobot.wake.android.configuration.AndroidTokenDoa;

public class AndroidTokenDoaTest extends InstTestTamplet {

    private AndroidTokenDoa doa;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        doa = new AndroidTokenDoa(sharedPreferences);
    }

    @Test
    public void overallTokenDoaTest() throws Exception {
        super.sharedPreferences.edit().clear();
        Assert.assertEquals("", doa.get());

        super.sharedPreferences.edit().clear();
        String input = "token";
        doa.save(input);
        Assert.assertEquals(input, doa.get());
    }
}