package io.rainrobot.wake.android.alarm;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import io.rainrobot.wake.android.activities.R;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Sound;
import io.rainrobot.wake.core.util.Log;

public class AlarmActivity extends AppCompatActivity {

    protected MediaPlayer player;
    protected AlarmEvent event;
    protected AndroidAlarmMgr androidAlarmMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        try { event = reCreateEvent(getIntent()); }
        catch (Exception e){
            Log.err("activity create event", e);
            return;
        }
        androidAlarmMgr = new AndroidAlarmMgr(this);
        setTurnOff();
        play();
    }

    protected void setLayout() {
        setContentView(R.layout.activity_alarm);
    }

    private void setTurnOff() {
        Button btn = findViewById(R.id.turnOffBtn);
        btn.setOnClickListener((v) -> finish());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPlayer();
    }

    protected void stopPlayer() {
        if(player != null) {
            player.release();
            player = null;
        }
    }

    private void play() {
        if(player == null) {
            int media = getMedia(event.getSound());
            player = MediaPlayer.create(this, media);
            player.setLooping(true);
        }
        player.start();
    }

    private int getMedia(Sound sound) {
        switch (sound) {
            case BELL:
                return R.raw.bells;
            case RING:
                return R.raw.ring;
            case BIRDS:
                return R.raw.birds;
            default: throw new NullPointerException("No sound found");
        }
    }

    private AlarmEvent reCreateEvent(Intent intent) {
        return new AndroidAlarmMgr.EventBundleConvertor()
                .toEvent(intent);
    }
}
