package io.rainrobot.wake.fx.config;

import io.rainrobot.wake.alarm.IAlarmMgr;
import io.rainrobot.wake.alarm.ISchedulerServiceMgr;
import io.rainrobot.wake.app.AppConfiguration;
import io.rainrobot.wake.app.AppContainer;
import io.rainrobot.wake.app.IDeviceDoa;
import io.rainrobot.wake.app.IEventDoa;
import io.rainrobot.wake.app.IRememberMeDoa;
import io.rainrobot.wake.app.ITokenDoa;
import io.rainrobot.wake.app.IViewFactory;
import io.rainrobot.wake.app.log.MyLogFile;
import io.rainrobot.wake.client.ASyncProvider;
import io.rainrobot.wake.client.IHttpRequestSender;
import io.rainrobot.wake.core.util.ILog;
import io.rainrobot.wake.core.util.Log;
import io.rainrobot.wake.core.util.Singleton;
import io.rainrobot.wake.fx.alarm.*;
import io.rainrobot.wake.fx.alarm.mvc.*;
import io.rainrobot.wake.fx.alarm.mvc.view.NoSnoozeView;
import io.rainrobot.wake.fx.alarm.mvc.view.SnoozeView;
import io.rainrobot.wake.fx.client.SpringHttpRequestSenderConfiguraton;

import io.rainrobot.wake.fx.doa.*;
import io.rainrobot.wake.fx.view.component.FxmlLoader;
import io.rainrobot.wake.fx.view.component.MainStageMgr;
import io.rainrobot.wake.fx.view.component.FxViewFactory;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class FxConfiguration extends AppConfiguration{

	private static final String ALARM = "alarm";
	public static final String SERVICE = "service";

	private Stage mainStage;
	private Singleton<SessionFactory> sFactory = new Singleton();
	private Singleton<AlarmControllerMgr> alarmCtrlMgr = new Singleton();
	private Singleton<StdSchedulerFactory> schedulerFactory = new Singleton();
	private Singleton<FxmlLoader> fxmlLoader = new Singleton<>();

	public FxConfiguration(Stage stage) {
		super(new AppContainer());
		this.mainStage = stage;
	}

	public FxConfiguration(AppContainer appContainer, Stage stage) {
		super(appContainer);
		this.mainStage = stage;
	}

	@Override
	public IHttpRequestSender getHttpSender() {
		return appContainer.sender.get(() -> {
				return new SpringHttpRequestSenderConfiguraton()
						.getRequestSender();
			}
		);
	}

	@Override
	public ISchedulerServiceMgr getAlarmSchedulerMgr() {
		return new FxSchedulerServiceMgr(getJobScheduler(SERVICE));
	}

	@Override
	public IAlarmMgr getAlarmMgr() {
		return appContainer.getAlarmMgr(
				() -> new FxAlarmMgr(getJobScheduler(ALARM)));
	}

	private Scheduler getJobScheduler(String tag) {
		try {
//			return getStdSchedulerFactory().getScheduler(tag);
			return getStdSchedulerFactory().getScheduler();
		}
		catch (SchedulerException e) { Log.err(tag, e); throw new NullPointerException(); }
	}

	private StdSchedulerFactory getStdSchedulerFactory() {
		return schedulerFactory.get(() -> new StdSchedulerFactory());
	}

	@Override
	public IRememberMeDoa getRememberMeDoa() {
		return appContainer.remeberMeDoa.get(
				() -> new FxPrefRMDoa());
	}

	private HibernateExecuter getHibernateExecuter() {
		return new HibernateExecuter(getSessionFactory());
	}

	private SessionFactory getSessionFactory() {
		return sFactory.get(() -> {
			return new Configuration()
//					.configure(UrlProvider.getHibernate().toString())
					.configure()
					.buildSessionFactory();
		});
	}

	@Override
	public IViewFactory getViewFactory() {
		return appContainer.getViewFactory(() ->
			new FxViewFactory(getMainStageMgr()));
	}

	private MainStageMgr getMainStageMgr() { return new MainStageMgr(mainStage, getFxmlLoader()); }

	private FxmlLoader getFxmlLoader() {
		return fxmlLoader.get(() -> new FxmlLoader(new FXMLLoader()));
	}

	@Override
	public IDeviceDoa getDeviceDoa() {
		return appContainer.deviceDoa.get(() -> new FxDeviceDoa());
	}

	@Override
	public IEventDoa getEventDoa() {
		return appContainer.eventDoa.get(() -> new FxEventDoa(getHibernateExecuter()));
	}

	@Override
	public ILog getLog() {
		return new FxLog();
	}

	@Override
	public ITokenDoa getTokenDoa() {
		return appContainer.tokenDoa.get(() -> new FxPrefTokenDoa());
	}

	@Override
	public ASyncProvider getAsyncProvider() {
		return new FxAsyncProvider();
	}


	public AlarmControllerMgr getAlarmControllerMgr() {
		return alarmCtrlMgr.get(
				() -> new AlarmControllerMgr(getSnoozeController(),
												getNoSnoozeController()));
	}

	public NoSnoozeController getNoSnoozeController() {
		return new NoSnoozeController(new NoSnoozeView(getFxmlLoader(), new Stage()));
	}

	public SnoozeController getSnoozeController() {
		return new SnoozeController(new SnoozeView(getFxmlLoader(), new Stage()),
								new SnoozeModel(getAlarmMgr()));
	}
}
