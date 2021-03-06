package io.rainrobot.wake.android.view.selectdevice;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.rainrobot.wake.android.activities.GoBackActivity;
import io.rainrobot.wake.android.activities.R;
import io.rainrobot.wake.android.activities.SelectDeviceActivity;
import io.rainrobot.wake.android.configuration.AndroidView;
import io.rainrobot.wake.android.configuration.ContextMgr;
import io.rainrobot.wake.controller.SelectDeviceController;
import io.rainrobot.wake.core.Device;
import io.rainrobot.wake.core.util.Log;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.SelectDeviceView;

public class AndroidSelectDeviceView    extends AndroidView<SelectDeviceActivity, SelectDeviceController>
                                        implements SelectDeviceView {

    int selectedDevicePosition = -1;
    private StringArrayAdapter adapter;
    List<Device> devices = new ArrayList<>();
    private List<String> names = new ArrayList<>();

    public AndroidSelectDeviceView(ContextMgr contextMgr) {
        super(contextMgr, SelectDeviceActivity.class);
    }

    @Override
    public void show() { super.show(); }

    @Override
    protected void initializeActivity() {
        setGoBackCommand(controller.getGoBackCommand());
        controller.addDevices();
        setAdapterToListView();
    }

    private void setAdapterToListView() {
        adapter = new StringArrayAdapter(activity, names);
        ListView listView = (ListView) findViewById(R.id.SelectDeviceList);
        boolean flag = (listView.getVisibility() == ListView.VISIBLE);
        Log.d(this, "is this viable = " + flag);
        listView.setAdapter(adapter);
        setOnListItemClick(listView);
    }

    private void setOnListItemClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(activity)
                        .setMessage("is this device " + devices.get(position).getName() + "?")
                        .setNegativeButton("No", (w, d) -> {})
                        .setPositiveButton("Yes", (w, d) -> {
                            Device device = devices.get(position);
                            controller.getSelectCommand().accept(device);
                        })
                        .create()
                        .show();
            }
        });
    }


    public void setGoBackCommand(Command command) {
        ((GoBackActivity)activity).setGoBackCommand(command);
    }

    @Override
    public void addDev(List<Device> devices) {
        for(Device dev : devices) {
            if(!names.contains(dev.getName())) {
                this.devices.add(dev);
                names.add(dev.getName());
            }
        }
        adapter.notifyDataSetChanged();
    }

//    @Override
//    public Device getDevice() {
//        if (selectedDevicePosition == -1) return null;
//        else return devices.get(selectedDevicePosition);
//    }
}

class StringArrayAdapter extends BaseAdapter {
    private Activity activity;
    private List<String> values;

    public StringArrayAdapter(Activity activity, List<String> values) {
        this.activity = activity;
        this.values = values;
    }

    public void add(String string) {
        values.add(string);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            TextView textView = new TextView(activity);
            textView.setText(values.get(position));
            textView.setTextSize(28);
            convertView = textView;
        }
        return convertView;
    }


}
