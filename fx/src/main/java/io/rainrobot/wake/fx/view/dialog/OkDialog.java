package io.rainrobot.wake.fx.view.dialog;

import io.rainrobot.wake.fx.view.component.MainStageMgr;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public abstract class OkDialog extends Dialog{

    protected HBox buttonBox = new HBox();
    protected Button okButton = new Button();

    public OkDialog(MainStageMgr mainStageMgr) {
        super(mainStageMgr);
        setUpButtonBox();
    }

    protected void setUpButtonBox() {
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(5));
        buttonBox.getChildren().add(okButton);
        okButton.setOnAction(e -> getOnOkClick());
        okButton.setText("Ok");
        root.getChildren().add(buttonBox);
    }

    protected void getOnOkClick() {
        super.close();
    }


}
