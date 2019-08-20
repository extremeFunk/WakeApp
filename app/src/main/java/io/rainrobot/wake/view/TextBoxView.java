package io.rainrobot.wake.view;

import io.rainrobot.wake.util.Command;

public interface TextBoxView {

	void initialize(String title, String msg);

	void show();

	void close();

	void setOkCommand(Command command);

	String getInput();

}
