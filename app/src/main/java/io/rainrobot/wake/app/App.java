package io.rainrobot.wake.app;

import java.util.function.Consumer;
import io.rainrobot.wake.client.ASyncProvider;
import io.rainrobot.wake.core.util.Log;

public class App {

	private ControllerMgr controllerMgr;
	private LoginMgr loginMgr;
	private ASyncProvider aSyncProvider;
	private DeviceInfoMgr deviceInfoMgr;
	private AppLogger logger;

	public App(ControllerMgr controllerMgr,
               LoginMgr loginMgr,
               ASyncProvider aSyncProvider,
               DeviceInfoMgr deviceInfoMgr,
               AppLogger logger) {

		this.controllerMgr = controllerMgr;
		this.loginMgr = loginMgr;
		this.aSyncProvider = aSyncProvider;
		this.deviceInfoMgr = deviceInfoMgr;
		this.logger = logger;
	}

	public void start() {
		if(loginMgr.isRememberMe()) {
			attemptLogin();
		}
		else controllerMgr.showLogin();
	}

	public void attemptLogin() {
		aSyncProvider.asyncCall(
				() -> loginMgr.isTokenValid(),
				(isValid) -> {
					if (isValid) logger.login();
					else controllerMgr.showLogin();
				},
				getExceptionHandler(),
				getDoLast());
	}

	public Runnable getDoLast() {
		return ()->{};
	}

	public Consumer<Exception> getExceptionHandler() {
		return (ex)-> {
					controllerMgr.showLogin();
					Log.err("problem loading", ex);
				};
	}
}
