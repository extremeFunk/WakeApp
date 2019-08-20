package io.rainrobot.wake.android.activities;

import io.rainrobot.wake.android.configuration.WakeActivity;

public class MainMenuActivity extends WakeActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_menu;
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
