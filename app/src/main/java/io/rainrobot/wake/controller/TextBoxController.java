package io.rainrobot.wake.controller;

import io.rainrobot.wake.util.Command;
import io.rainrobot.wake.view.TextBoxView;


public class TextBoxController {
	
	TextBoxView view;

	public TextBoxController(TextBoxView view) {
		this.view = view;
	}

	public void show(String title, String msg) {
		view.initialize(title, msg);
		view.show();	
	}
	
	public String getInput() {
		return view.getInput();
	}

	public void setOnOkCommand(Command command) {
		view.setOkCommand(command);
	}

	public void close() {
		view.close();
	}
	
	

}
