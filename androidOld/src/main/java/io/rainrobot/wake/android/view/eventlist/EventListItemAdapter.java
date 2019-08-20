package io.rainrobot.wake.android.view.eventlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import io.rainrobot.wake.android.activities.EventListActivity;
import io.rainrobot.wake.android.activities.R;
import io.rainrobot.wake.core.AlarmEvent;

public class EventListItemAdapter extends BaseAdapter {

    private final List<AlarmEvent> eventList;
    private final EventListActivity activity;
    private Consumer<Integer> removeCmd;
    private AlarmEventEditDialogFactory dialogFactory;

    public EventListItemAdapter(EventListActivity activity,
                                List<AlarmEvent> eventList,
                                AlarmEventEditDialogFactory dialogFactory,
                                Consumer<Integer> removeCmd) {
        this.eventList = eventList;
        this.activity = activity;
        this.dialogFactory = dialogFactory;
        dialogFactory.setParentListAdapter(this);
        this.removeCmd = removeCmd;
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return eventList.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = LayoutInflater.from(activity)
                .inflate(R.layout.item_event_list, parent, false);
        AlarmEvent event = eventList.get(position);

        setDelay(view, event);
        setSnooze(view, event);
        setSound(view, event);
        setShutoff(view, event);
        setVol(view, event);
        setDevice(view, event);

        setDelBtn(view, event);
        setEditBtn(view, event);

        return view;
    }

    private void setEditBtn(View view, AlarmEvent event) {
        Button editBtn = view.findViewById(R.id.eventItemEditBtn);
        editBtn.setOnClickListener((v) -> createEditDialog(event));
    }

    private void setDelBtn(View view, AlarmEvent event) {
        Button deleteBtn = view.findViewById(R.id.eventItemDeleteBtn);
        deleteBtn.setOnClickListener((v) -> removeCmd.accept(event.getId()));
    }

    private void setDevice(View view, AlarmEvent event) {
        TextView deviceTv = view.findViewById(R.id.eventItemDevice);
        deviceTv.setText(event.getDevice().getName());
    }

    private void setVol(View view, AlarmEvent event) {
        TextView volTv = view.findViewById(R.id.eventItemVol);
        volTv.setText(AlarmEvent.VOL_PREFIX + event.getVol());
    }

    private void setSound(View view, AlarmEvent event) {
        TextView soundTv = view.findViewById(R.id.eventItemSound);
        soundTv.setText(event.getSound().name());
    }

    private void setDelay(View view, AlarmEvent event) {
        TextView delayTv = view.findViewById(R.id.eventItemDelay);
        delayTv.setText(event.getDelay() + AlarmEvent.DELAY_SUFFIX);
    }

    private void setShutoff(View view, AlarmEvent event) {
        TextView shutoffTv = view.findViewById(R.id.eventItemShutoff);
        int shutOff = event.getShutOff();
        if(shutOff == AlarmEvent.SNOOZE_OFF) {
            shutoffTv.setText("off");
        } else {
            shutoffTv.setText(shutOff + AlarmEvent.SHUTOFF_SUFFIX);
        }
    }

    private void setSnooze(View view, AlarmEvent event) {
        TextView snoozeTv = view.findViewById(R.id.eventItemSnooze);
        int snooze = event.getSnooze();
        if(snooze == AlarmEvent.SNOOZE_OFF) {
            snoozeTv.setText("off");
        } else {
            snoozeTv.setText(snooze + AlarmEvent.SNOOZE_SUFFIX);
        }
    }

    private void createEditDialog(AlarmEvent event) {
        dialogFactory.create(event).show();
    }


    public void remove(int id) {
        Optional<AlarmEvent> optional = eventList.stream()
                                            .filter(e -> e.getId().equals(id))
                                            .findFirst();
        if (optional.isPresent()) {
            eventList.remove(optional.get());
            notifyDataSetChanged();
        } else throw new RuntimeException("event with id " + id + " was not found");
    }

    public void addEvent(AlarmEvent e) {
        eventList.add(e);
        notifyDataSetChanged();
    }

    public void clear() {
        eventList.clear();
        notifyDataSetChanged();
    }

    public void updateEvent(AlarmEvent updateEvent) {
        for(int i = 0; i <= eventList.size(); i++) {
            AlarmEvent e = eventList.get(i);
            if(e.getId() == updateEvent.getId()) {
               eventList.set(i, updateEvent);
               break;
            }
        }
        this.notifyDataSetChanged();
    }
}