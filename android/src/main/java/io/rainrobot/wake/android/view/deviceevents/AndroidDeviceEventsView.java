package io.rainrobot.wake.android.view.deviceevents;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.rainrobot.wake.android.activities.DeviceEventsActivity;

import io.rainrobot.wake.android.activities.R;
import io.rainrobot.wake.android.configuration.AndroidView;
import io.rainrobot.wake.android.configuration.ContextMgr;
import io.rainrobot.wake.controller.DeviceEventsController;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.core.util.DateUtil;
import io.rainrobot.wake.view.DeviceEventsView;

public class AndroidDeviceEventsView extends AndroidView<DeviceEventsActivity,
                                                            DeviceEventsController>
                                            implements DeviceEventsView {

    private List<View> tableRows = new ArrayList<>();

    public AndroidDeviceEventsView(ContextMgr contextMgr) {
        super(contextMgr, DeviceEventsActivity.class);
    }

    @Override
    public void setEvents(AlarmEvent[] events) {
        TableLayout table = activity.findViewById(R.id.device_event_table);
        LayoutInflater inflater = activity.getLayoutInflater();
        for(AlarmEvent e : events) {
            View row = inflater.inflate(R.layout.device_event_row_layout, table);
            //time
            TextView tvTime = row.findViewById(R.id.cellTime);
            tvTime.setText(DateUtil.toHrMnTxt(e.getTime()));
            //sound
            TextView tvSound = row.findViewById(R.id.cellSound);
            tvSound.setText(e.getSound().name());
            //vol
            TextView tvVol = row.findViewById(R.id.cellVol);
            tvVol.setText(AlarmEvent.VOL_PREFIX + e.getVol());
            //snooze
            TextView tvSnooze = row.findViewById(R.id.cellSnooze);
            if(e.getSnooze() == AlarmEvent.SNOOZE_OFF) tvSnooze.setText("off");
            else  tvSnooze.setText(e.getSnooze() + AlarmEvent.SNOOZE_SUFFIX);
            //shutoff
            TextView tvShutoff = row.findViewById(R.id.cellShutoff);
            if(e.getShutOff() == AlarmEvent.SHUTOFF_OFF) tvShutoff.setText("off");
            else tvShutoff.setText(e.getShutOff() + AlarmEvent.SHUTOFF_SUFFIX);

            tableRows.add(row);
        }
    }


    public void setGoBackCommand(Command command) {
        ((DeviceEventsActivity)activity).setGoBackCommand(command);
    }

    @Override
    protected void initializeActivity() {
       setGoBackCommand(controller.getGoBackCmd());
       controller.injectDataToView();
    }

}
