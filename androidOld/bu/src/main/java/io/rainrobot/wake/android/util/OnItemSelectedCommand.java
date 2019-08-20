package io.rainrobot.wake.android.util;

import android.view.View;
import android.widget.AdapterView;

public interface OnItemSelectedCommand {
    void onItemSelected(AdapterView<?> parent, View view, int position, long id);
}
