package io.rainrobot.wake.android.activities;

import android.os.Bundle;

import io.rainrobot.wake.android.configuration.AndroidAppRunner;
import io.rainrobot.wake.android.configuration.WakeActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.concurrent.CompletableFuture;

public class RunActivity extends WakeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CompletableFuture.runAsync(this::animateSplash);
        AndroidAppRunner.run(getWakeApplication());
    }

    private void animateSplash() {
        YoYo.with(Techniques.Tada)
                .duration(1500)
                .repeat(5)
                .playOn(findViewById(R.id.splash_img_view));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.splash_screen;
    }

}
