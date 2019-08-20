package io.rainrobot.wake.android.activities;

import android.app.Activity;
import android.app.AlertDialog;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.function.Consumer;

public class InputDialogBuilder {

    private final Activity activity;
    private final AlertDialog.Builder dialogBuilder;
    private final View view;

    public InputDialogBuilder(Activity activity) {
        this.activity = activity;
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.input_box, null);
        dialogBuilder = new AlertDialog.Builder(activity)
                .setView(view);
    }

    public InputDialogBuilder setMsg(String msg) {
        dialogBuilder.setMessage(msg);
        return this;
    }

    public InputDialogBuilder setOnCancelCmd(Runnable run) {
        dialogBuilder.setNegativeButton("Cancel", (v, w) -> run.run());
        return this;
    }

    public InputDialogBuilder setOnOk(Consumer<String> onOk) {
        dialogBuilder.setPositiveButton("Ok", (v, w) -> {
            EditText eTxt =  view.findViewById(R.id.InputBoxInputTxt);
            onOk.accept(eTxt.getText().toString());
        });
        return this;
    }

    public void show() {
        dialogBuilder.show();
    }
}
