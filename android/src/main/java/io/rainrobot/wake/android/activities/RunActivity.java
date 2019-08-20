package io.rainrobot.wake.android.activities;

import android.os.Bundle;

import io.rainrobot.wake.android.configuration.AndroidAppRunner;
import io.rainrobot.wake.android.configuration.WakeActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class RunActivity extends WakeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animateSplash();
        AndroidAppRunner.run(getWakeApplication());
    }

    private void animateSplash() {
        YoYo.with(Techniques.Tada)
                .duration(1500)
                .repeat(2)
                .playOn(findViewById(R.id.think_img_view));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.splash_screen;
    }
}