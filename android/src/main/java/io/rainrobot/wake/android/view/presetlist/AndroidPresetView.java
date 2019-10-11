package io.rainrobot.wake.android.view.presetlist;


import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import io.rainrobot.wake.android.activities.GoBackActivity;
import io.rainrobot.wake.android.activities.InputDialogBuilder;
import io.rainrobot.wake.android.activities.PresetListActivity;
import io.rainrobot.wake.android.activities.R;
import io.rainrobot.wake.android.configuration.AndroidView;
import io.rainrobot.wake.android.configuration.ContextMgr;
import io.rainrobot.wake.controller.PresetsController;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.core.util.DateUtil;
import io.rainrobot.wake.util.IdCommand;
import io.rainrobot.wake.view.PresetsView;

public class AndroidPresetView extends AndroidView<PresetListActivity, PresetsController> implements PresetsView {

    private PresetListAdapter adapter;
    private ListView listView;

    public AndroidPresetView(ContextMgr contextMgr) {
        super(contextMgr, PresetListActivity.class);
    }

    @Override
    public void show() {
        if(adapter != null) {
            adapter.clear();
        }
        super.show();
    }

    private PresetListAdapter buildAdapter() {
        PresetListAdapter adapter = new PresetListAdapter(activity);

        return adapter.setActivieStateCommand(controller.getActiveStateCommand())
                .setDeleteCommand(controller.getDeleteCommand())
                .setEditCommand(controller.getEditCommand())
                .setRenameCommand(controller.getRenameCommand())
                .setTimeCommand(controller.getTimeCommand());
    }


    public void setAddCommand(Command command) {
        activity.findViewById(R.id.presetListBtnAdd)
            .setOnClickListener((v) -> command.execute());
    }


    public void setGoBackCommand(Command command) {
        ((GoBackActivity)activity).setGoBackCommand(command);
    }
    @Override
    public void addRow(Preset preset) {
        adapter.add(preset);
    }

    @Override
    public void removeRow(int id) { adapter.remove(id); }

    @Override
    public void updateName(int id, String name) {
        Optional<Preset> optional = getPresetById(id);
        if (optional.isPresent()) {
            optional.get().setName(name);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateTime(int id, Date time) {
        Optional<Preset> optional = getPresetById(id);
        optional.get().setTime(time);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateActiveState(int id, boolean state) {
        Optional<Preset> optional = getPresetById(id);
        optional.get().setActiveState(state);
        adapter.notifyDataSetChanged();
    }

    private Optional<Preset> getPresetById(int id) {
        return adapter.getItemList()
                .stream()
                .filter(p -> Integer.valueOf(p.getId())
                .equals(Integer.valueOf(id)))
                .findFirst();
    }

    @Override
    protected void initializeActivity() {
        listView = activity.findViewById(R.id.PresetListListView);
        adapter = buildAdapter();
        listView.setAdapter(adapter);
        controller.injectDataToView();
        setAddCommand(controller.getAddCommand());
        setGoBackCommand(controller.getGoBackCommand());
    }
}



class PresetListAdapter extends BaseAdapter {
    private final List<Preset> list = new ArrayList<>();
    private Context context;

    private IdCommand deleteCommand;
    private IdCommand editCommand;
    private PresetsController.NameCommand renameCommand;
    private PresetsController.ActiveStateCommand activieStateCommand;
    private PresetsController.TimeCommand timeCommand;


    public PresetListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(this.context);
        Preset preset = list.get(position);
        view = inflater.inflate(R.layout.item_preset_list, null);

        setValuesToView(view, preset, position);
        return view;
    }

    private void setValuesToView(View view, Preset preset, int itemPos) {
        setClickActions(view, preset, itemPos);
    }


    private void setClickActions(View view, Preset preset, int itemPos) {

        Button deleteButton = view.findViewById(R.id.deleteButtonPresetItem);
        deleteButton.setOnClickListener((v) -> deleteCommand.execute(preset.getId()));

        Button editButton = view.findViewById(R.id.editButtonPresetItem);
        editButton.setOnClickListener((v) -> editCommand.execute(preset.getId()));

        TextView timeTV = view.findViewById(R.id.timeTextViewPresetItem);
        timeTV.setText(DateUtil.toHrMnTxt(preset.getTime()));
        timeTV.setOnClickListener((v) -> createTimePickerDialog(view, preset));

        Button nameBtn = view.findViewById(R.id.nameButtonPresetItem);
        nameBtn.setOnClickListener((v) -> createNameDialog(context, preset));
        nameBtn.setText(preset.getName());

        //active state toggle action
        ToggleButton toggle = view.findViewById(R.id.alarmTogglePresetItem);
        toggle.setChecked(preset.isActiveState());
        toggle.setOnCheckedChangeListener((btn, state) -> {
            activieStateCommand.execute(preset.getId(), state);
        });
    }

    private void createNameDialog(Context view, Preset preset) {
        new InputDialogBuilder((Activity)context)
                .setMsg("Change Preset Name: ")
                .setOnCancelCmd(() -> {})
                .setOnOk((input) -> renameCommand.execute(preset.getId(), input))
                .show();
    }

    private void createTimePickerDialog(View view, Preset preset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(preset.getTime());
        int presetHr = cal.get(Calendar.HOUR_OF_DAY);
        int presetMn  = cal.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog
                = getTimePickerDialogInstance(view, preset, presetHr, presetMn);
        timePickerDialog.show();
    }

    @NonNull
    private TimePickerDialog getTimePickerDialogInstance(View view,
                                                         Preset preset,
                                                         int presetHr,
                                                         int presetMn) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener
                = (timePickView, h, m) -> {
            timeCommand.execute(preset.getId(),
                    DateUtil.fromHrAndMn(h, m));
        };

        return new TimePickerDialog(view.getContext(),
                onTimeSetListener,
                presetHr, presetMn, true);
    }


    public PresetListAdapter setDeleteCommand(IdCommand deleteCommand) {
        this.deleteCommand = deleteCommand;
        return this;
    }

    public PresetListAdapter setEditCommand(IdCommand editCommand) {
        this.editCommand = editCommand;
        return this;
    }

    public PresetListAdapter setRenameCommand(PresetsController.NameCommand renameCommand) {
        this.renameCommand = renameCommand;
        return this;
    }

    public PresetListAdapter setActivieStateCommand(PresetsController
                                                            .ActiveStateCommand cmd) {
        this.activieStateCommand = cmd;
        return this;
    }

    public PresetListAdapter setTimeCommand(PresetsController
                                                    .TimeCommand cmd) {
        this.timeCommand = cmd;
        return this;
    }

    public void clear() {
        list.clear();
        super.notifyDataSetChanged();
    }

    public void add(Preset preset) {
        if(!list.contains(preset)) {
            list.add(preset);
            notifyDataSetChanged();
        }
    }

    public void remove(int id) {
        list.removeIf(ps -> ps.getId().equals(id));
        notifyDataSetChanged();
    }

    public List<Preset> getItemList() {
        return this.list;
    }
}

