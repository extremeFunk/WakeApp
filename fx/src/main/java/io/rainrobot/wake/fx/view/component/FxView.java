package io.rainrobot.wake.fx.view.component;

import java.net.URL;

import io.rainrobot.wake.app.AbstractController;
import io.rainrobot.wake.app.IView;
import io.rainrobot.wake.fx.view.dialog.MsgDialog;
import io.rainrobot.wake.fx.view.dialog.ThinkDialog;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class FxView <T extends AbstractController> implements IView<T>, Initializable {
	
	protected final MainStageMgr mainWindowMgr;
	private MsgDialog msgDialog;
//	private Stage oldThinkDialog;
//	private ThinkDialog thinkDialog;

	public FxView(MainStageMgr mainWindowMgr) {
		this.mainWindowMgr = mainWindowMgr;
		this.msgDialog = new MsgDialog(mainWindowMgr);
//		this.thinkDialog = new ThinkDialog(mainWindowMgr);
	}

	public void show() {
		URL url = getClass().getResource("/fxml/view/" + getFxmlName() + ".fxml");
		mainWindowMgr.load(url, this);
		mainWindowMgr.show();
	}
	
	protected abstract String getFxmlName();
	
	@Override
	public abstract void setController(T controller);
	
	@Override
	public void showMsg(String msg) {
		msgDialog.show(msg);
	}

	@Override
	public void startThinkMode() { mainWindowMgr.startThinkMode(); }

	@Override
	public void stopThinkMode() { mainWindowMgr.stopThinkMode(); }

//	@Deprecated
//	protected void oldShowMsg(String string) {
//		Stage stage = new Stage();
//		stage.setAlwaysOnTop(true);
//		Parent root = new Label(string);
//		stage.setScene(new Scene(root));
//		stage.show();
//	}
//	@Deprecated
//	protected void oldStartThink() {
//		thinkMode++;
//		if(thinkMode > thinkModeMinimum) return;
//		mainWindowMgr.disable();
//		if(oldThinkDialog != null && oldThinkDialog.isShowing()) {
//			oldThinkDialog.close(); oldThinkDialog = null;
//		}
//		oldThinkDialog = new Stage();
//		oldThinkDialog.setAlwaysOnTop(true);
//		Parent root = new Label("working");
//		oldThinkDialog.setScene(new Scene(root));
//		oldThinkDialog.initStyle(StageStyle.UNDECORATED);
//		oldThinkDialog.initModality(Modality.APPLICATION_MODAL);
//		oldThinkDialog.show();

//	}
//	@Deprecated
//	protected void oldStopthinkMode() {
//		thinkMode--;
//		if(thinkMode < thinkModeMinimum) {
//			mainWindowMgr.enable();
//			oldThinkDialog.close();
//		}
//	}
}
