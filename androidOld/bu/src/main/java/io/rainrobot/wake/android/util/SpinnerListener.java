package io.rainrobot.wake.android.util;

import android.view.View;
import android.widget.AdapterView;

public class SpinnerListener implements AdapterView.OnItemSelectedListener {

    private final OnItemSelectedCommand command;

    public SpinnerListener (OnItemSelectedCommand command) {
        this.command = command;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        command.onItemSelected(parent, view, position, id);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}




