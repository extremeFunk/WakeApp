package io.rainrobot.wake.fx.config;

import io.rainrobot.wake.core.util.ILog;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FxLog implements ILog {

    Logger log;
//    MyLogFile file = new MyLogFile();

    public FxLog() {
//        PropertyConfigurator.configure(getClass().getResource("/log4j.xml"));
        BasicConfigurator.configure();
        log = LoggerFactory.getLogger(FxLog.class);
    }

    @Override
    public void i(String tag, String msg) {
        log.info(tag + msg);
//        file.log(tag + msg);

    }

    @Override
    public void d(String tag, String msg) {
        log.debug(tag + msg);
//        file.log(tag + msg);
    }

    @Override
    public void e(String tag, String msg) {
        log.error(tag + msg);
//        file.log(tag + msg);
    }

    @Override
    public void e(String tag, StackTraceElement[] stackTrace) {
        log.error(tag);
//        file.log(tag);
        for(StackTraceElement e : stackTrace) {
            log.error(e.toString());
//            file.log(e.toString());
        }
    }
}
