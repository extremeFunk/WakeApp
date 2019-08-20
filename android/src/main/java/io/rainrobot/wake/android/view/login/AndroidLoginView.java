package io.rainrobot.wake.android.view.login;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import io.rainrobot.wake.android.activities.LoginActivity;
import io.rainrobot.wake.android.activities.R;
import io.rainrobot.wake.android.configuration.AndroidView;
import io.rainrobot.wake.android.configuration.ContextMgr;
import io.rainrobot.wake.controller.LoginController;
import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.LoginView;

public class AndroidLoginView extends AndroidView<LoginActivity, LoginController> implements LoginView {

	public AndroidLoginView(ContextMgr contextMgr) {
		super(contextMgr, LoginActivity.class);
	}

	@Override
	public void setLoginCommand(Command command) {
		Button btn = activity.findViewById((R.id.LoginBtnLogin));
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				closeInputMethod();
				command.execute();
			}
		});
	}

	protected void closeInputMethod() {
		InputMethodManager imm;
		imm = (InputMethodManager)activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		EditText passET = activity.findViewById(R.id.loginTxtBoxPassword);
		if(imm.isActive(passET)) {
			imm.hideSoftInputFromWindow(passET.getWindowToken(), 0);
		}

		EditText userET = activity.findViewById(R.id.enterNewPasswordEditTxt);
		if (imm.isActive(userET)) {
			imm.hideSoftInputFromWindow(userET.getWindowToken(), 0);
		}
	}

	@Override
	public void setSingupCommand(Command command) {
		Button btn = activity.findViewById((R.id.LoginBtnSingup));
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				command.execute();
			}
		});
	}

	@Override
	public void setForgotPasswordCommand(Command command) {

	}

	@Override
	public String getUsernameField() {
		return  ((EditText)activity.findViewById(R.id.enterNewPasswordEditTxt))
				.getText()
				.toString();
	}

	@Override
	public String getPasswordField() {
		return  ((EditText)activity.findViewById(R.id.loginTxtBoxPassword))
				.getText()
				.toString();
	}

	@Override
	public Boolean isRememberMe() {
		return ((CheckBox)activity.findViewById(R.id.loginCheckBoxRememberMe))
				.isChecked();
	}

	@Override
	protected void initializeActivity() {
		setLoginCommand(controller.getLoginCommand());
		setSingupCommand(controller.getSingupCommand());
	}

}
