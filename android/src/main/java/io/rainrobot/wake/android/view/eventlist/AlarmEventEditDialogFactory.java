package io.rainrobot.wake.android.view.eventlist;

import android.app.Activity;

import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import io.rainrobot.wake.android.activities.R;
import io.rainrobot.wake.android.layout.VerticalViewPager;
import io.rainrobot.wake.android.util.CarouselStringActionAdapter;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Device;
import io.rainrobot.wake.core.Sound;
import io.rainrobot.wake.core.util.Collections;
import io.rainrobot.wake.core.util.OrderdMap;

public class AlarmEventEditDialogFactory {

    private final Activity activity;
    private final Consumer<AlarmEvent> updateCmd;
    private final List<Device> devices;
    private EventListItemAdapter parentListAdapter;

    private AlarmEvent bufferEvent;

    public AlarmEventEditDialogFactory(Activity activity,
                                       Consumer<AlarmEvent> updateCmd, List<Device> devices) {
        this.activity = activity;
        this.updateCmd = updateCmd;
        this.devices = devices;
    }

    public AlertDialog create(AlarmEvent event) {
        this.bufferEvent = event.clone();
        View editView = inflateView();
        setParamsToView(editView);
        return buildDialog(editView);
    }

    private AlertDialog buildDialog(View editView) {
        return new AlertDialog.Builder(activity)
                .setView(editView)
                .setCancelable(false)
                .setPositiveButton("Ok", (w, d) -> {
                    onOkCmd();
                })
                .setNegativeButton("Cancel", (w, d) -> {
                })
                .create();
    }

    private void setParamsToView(View editView) {
        setDelayToView(editView);
        setSnoozeToView(editView);
        setShutoffToView(editView);

        setVolToView(editView);
        setSoundToView(editView);
        setDevicesToView(editView);
    }

    private View inflateView() {
        LayoutInflater inflater = LayoutInflater.from(activity);
        return inflater.inflate(R.layout.dialog_edit_event,
                null, false);
    }

    private void onOkCmd() {
        updateCmd.accept(bufferEvent);
        parentListAdapter.updateEvent(bufferEvent);
    }

    private void setSoundToView(View editView) {
        Spinner soundSpinner = editView.findViewById(R.id.eventDialogSoundSpinner);
        OrderdMap<Sound, String> soundMap = getSoundMap();
        soundSpinner.setAdapter(
                new StringActionSpinnerAdapter(activity, soundMap));
        soundSpinner.setSelection(soundMap.indexOf(bufferEvent.getSound()));
        setSoundListener(soundSpinner);
    }

    private void setSoundListener(Spinner soundSpinner) {
        soundSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sound sound = (Sound)parent.getItemAtPosition(position);
                bufferEvent.setSound(sound);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setDevicesToView(View editView) {
        Spinner deviceSpinner = editView.findViewById(R.id.eventDialogDeviceSpinner);
        OrderdMap<Device, String> deviceMap = getDeviceMap();
        deviceSpinner.setAdapter(
                new StringActionSpinnerAdapter(activity, deviceMap));
        deviceSpinner.setSelection(deviceMap.indexOf(bufferEvent.getDevice()));
        setDeviceListener(deviceSpinner);
    }

    private void setDeviceListener(Spinner deviceSpinner) {
        deviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Device device = (Device) parent.getItemAtPosition(position);
                bufferEvent.setDevice(device);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private OrderdMap<Device, String> getDeviceMap() {
        OrderdMap<Device, String> map = new OrderdMap<>();
        for (Device d : devices) map.put(d, d.getName());
        return map;
    }

    private OrderdMap<Sound, String> getSoundMap() {
        OrderdMap<Sound, String> map = new OrderdMap<>();
        for (Sound s : Sound.values()) map.put(s, s.name());
        return map;
    }


    private void setVolToView(View editView) {
        Consumer<Integer> onVolChangedCmd = (vol) -> bufferEvent.setVol(vol);
        SeekBar volumeBar = editView.findViewById(R.id.eventDialogVolSeekBar);
        volumeBar.setProgress(bufferEvent.getVol());
        TextView volTv = editView.findViewById(R.id.eventDialogVolTxtDisplay);
        volTv.setText(AlarmEvent.VOL_PREFIX + bufferEvent.getVol());
        volumeBar.setOnSeekBarChangeListener(
                getSeekBarCListener(onVolChangedCmd, volTv));
    }

    @NonNull
    private SeekBar.OnSeekBarChangeListener getSeekBarCListener(Consumer<Integer> onVolChangedCmd, TextView volTv) {
        return new SeekBar.OnSeekBarChangeListener() {

            private int prog;

            @Override
            public void onProgressChanged(SeekBar seekBar,
                                          int progress,
                                          boolean fromUser) {
                prog = progress;
                volTv.setText(AlarmEvent.VOL_PREFIX + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                return;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                onVolChangedCmd.accept(prog);
            }
        };
    }


    private void setShutoffToView(View editView) {
        VerticalViewPager pager = editView.findViewById(R.id.eventDialogShutoffPager);
        CarouselStringActionAdapter<Integer> adapter
                = new CarouselStringActionAdapter(getShutoffMap(), activity);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageSelected(int i) {bufferEvent.setShutOff(adapter.getItem(i));}
            @Override public void onPageScrolled(int i, float v, int i1) { }
            @Override public void onPageScrollStateChanged(int i) { }
        });
        int slctIndx = adapter.getItemPosition(bufferEvent.getShutOff());
        pager.setCurrentItem(slctIndx);
        setShutoffSwitch(editView, pager, adapter);
    }


    private void setShutoffSwitch(View editView, VerticalViewPager shutoffPgr, CarouselStringActionAdapter<Integer> adapter) {
        Switch shutoffSwitch = editView.findViewById(R.id.eventDialogShutoffSwitch);
        shutoffSwitch.setOnCheckedChangeListener((bView, activeState) -> {
            setShutoffActiveState(shutoffPgr, adapter, activeState);
        });
        boolean activeState = bufferEvent.getShutOff() != AlarmEvent.SHUTOFF_OFF;
        shutoffSwitch.setChecked(activeState);
        setShutoffActiveState(shutoffPgr, adapter, activeState);
    }

    private void setShutoffActiveState(VerticalViewPager pager,
                                       CarouselStringActionAdapter<Integer> adapter,
                                       boolean active) {
        if (active) {
            pager.setVisibility(View.VISIBLE);
            if(pager.getCurrentItem() == -1) {
                pager.setCurrentItem(adapter.getItemPosition(AlarmEvent.SNOOZE_MIN));
            }
            bufferEvent.setShutOff(adapter.getItem(pager.getCurrentItem()));
        } else {
            pager.setVisibility(View.INVISIBLE);
            bufferEvent.setShutOff(AlarmEvent.SNOOZE_OFF);
        }
    }

    private void setSnoozeToView(View editView) {
        VerticalViewPager snoozePgr = editView.findViewById(R.id.eventDialogSnoozePager);
        CarouselStringActionAdapter<Integer> adapter = new CarouselStringActionAdapter(getSnoozeMap(), activity);
        snoozePgr.setAdapter(adapter);
        snoozePgr.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageSelected(int i) { bufferEvent.setSnooze(adapter.getItem(i));}
            @Override public void onPageScrolled(int i, float v, int i1) {}
            @Override public void onPageScrollStateChanged(int i) {}
        });
        int slctIndx = adapter.getItemPosition(bufferEvent.getSnooze());
        snoozePgr.setCurrentItem(slctIndx);
        setSnoozeSwitch(editView, snoozePgr, adapter);
    }

    private void setSnoozeSwitch(View editView,
                                 VerticalViewPager snoozePgr,
                                 CarouselStringActionAdapter<Integer> adapter) {
        Switch snoozeSwitch = editView.findViewById(R.id.eventDialogSnoozeSwitch);
        snoozeSwitch.setOnCheckedChangeListener(
                (bView, activeState) -> { setSnoozeActiveState(snoozePgr, adapter, activeState); });
        boolean activeState = bufferEvent.getSnooze() != AlarmEvent.SNOOZE_OFF;
        snoozeSwitch.setChecked(activeState);
        setSnoozeActiveState(snoozePgr, adapter, activeState);
    }

    private void setSnoozeActiveState(VerticalViewPager snoozePgr,
                                      CarouselStringActionAdapter<Integer> adapter,
                                      boolean active) {
        if (active) {
            snoozePgr.setVisibility(View.VISIBLE);
            if(snoozePgr.getCurrentItem() == -1) {
                snoozePgr.setCurrentItem(adapter.getItemPosition(AlarmEvent.SNOOZE_MIN));
            }
            bufferEvent.setSnooze(adapter.getItem(snoozePgr.getCurrentItem()));
        } else {
            snoozePgr.setVisibility(View.INVISIBLE);
            bufferEvent.setSnooze(AlarmEvent.SNOOZE_OFF);
        }
    }

    private void setDelayToView(View editView) {
        VerticalViewPager delayPgr = editView.findViewById(R.id.eventDialogDelayPager);
        CarouselStringActionAdapter<Integer> adapter
                = new CarouselStringActionAdapter(getDelayMap(), activity);
        delayPgr.setAdapter(adapter);
        delayPgr.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageSelected(int i) { bufferEvent.setDelay(adapter.getItem(i));}
            @Override public void onPageScrolled(int i, float v, int i1) {}
            @Override public void onPageScrollStateChanged(int i) {}
        });
        int slctIndx = adapter.getItemPosition(bufferEvent.getDelay());
        delayPgr.setCurrentItem(slctIndx);
    }

    private OrderdMap<Integer, String> getShutoffMap() {
        Function<Integer, String> parser = (i) -> i + AlarmEvent.SHUTOFF_SUFFIX;
        List<Integer> keys = Collections.createIntSequence(AlarmEvent.SHUTOFF_MIN,
                                                            AlarmEvent.SHUTOFF_MAX,
                                                            AlarmEvent.SHUTOFF_INTERVAL);
        return Collections.getStringMap(keys, parser);
    }

    private OrderdMap<Integer, String> getSnoozeMap() {
        Function<Integer, String> parser = (i) -> i + AlarmEvent.DELAY_SUFFIX;
        List<Integer> keys = Collections.createIntSequence(AlarmEvent.SNOOZE_MIN, AlarmEvent.SNOOZE_MAX);
        return Collections.getStringMap(keys, parser);
    }

    private OrderdMap<Integer, String> getDelayMap() {
        Function<Integer, String> parser = (i) -> i + AlarmEvent.SNOOZE_SUFFIX;
        List<Integer> keys = Collections.createIntSequence(AlarmEvent.DELAY_MIN, AlarmEvent.DELAY_MAX);
        return Collections.getStringMap(keys, parser);
    }

    public void setParentListAdapter(EventListItemAdapter parentListAdapter) {
        this.parentListAdapter = parentListAdapter;
    }
}

class StringActionSpinnerAdapter<V> implements SpinnerAdapter {

    private Activity context;
    private OrderdMap<V, String> map;

    public StringActionSpinnerAdapter(Activity context, OrderdMap<V, String> map) {
        this.context = context;
        this.map = map;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setText(map.getValueAt(position));
        return textView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public Object getItem(int position) {
        return map.getKeyAt(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getDropDownView(position, convertView, parent);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
