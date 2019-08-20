package io.rainrobot.wake.android.activities;

import android.content.Intent;
import io.rainrobot.wake.android.configuration.WakeActivity;

public class MainMenuActivity extends WakeActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_menu;
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
