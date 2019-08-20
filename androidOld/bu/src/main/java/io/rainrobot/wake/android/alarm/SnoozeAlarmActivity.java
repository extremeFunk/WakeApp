package io.rainrobot.wake.android.alarm;

import android.os.Bundle;
import android.widget.Button;

import java.util.Date;

import io.rainrobot.wake.android.activities.R;
import io.rainrobot.wake.util.DateUtil;

public class SnoozeAlarmActivity extends AlarmActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSnoozeBtn();
    }

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_snooze_alarm);
    }

    private void setSnoozeBtn() {
        Button btn = findViewById(R.id.snoozeBtn);
        btn.setOnClickListener((v) -> snooze());
    }

    private void snooze() {
        androidAlarmMgr.addAlarm(event, DateUtil.plusMinuets(new Date(), event.getSnooze()));
        stopPlayer();
        finish();
    }

}
