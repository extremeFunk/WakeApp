package io.rainrobot.wake.android.view.login;


import android.view.View;
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
				command.execute();
			}
		});
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
	public String getUsernameField() {
		return  ((EditText)activity.findViewById(R.id.loginTxtBoxUsername))
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
