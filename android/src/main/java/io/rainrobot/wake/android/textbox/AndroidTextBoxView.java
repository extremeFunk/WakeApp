package io.rainrobot.wake.android.textbox;

import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.TextBoxView;

public class AndroidTextBoxView implements TextBoxView {

//    InputDialog dialog = new InputDialog();

    @Override
    public void initialize(String title, String msg) {
//        dialog.setTitle(title);
//        dialog.showMsg(msg);
    }

    @Override
    public void show() {
//        dialog.show();
    }

    @Override
    public void close() {
//        if(dialog.isVisible()) {
//            dialog.onDestroy();
//        }
    }

    @Override
    public void setOkCommand(Command command) {
//        dialog.setOnOkCmd(command);
    }

    @Override
    public String getInput() {
//        return dialog.getInput();
        return null;
    }
}
