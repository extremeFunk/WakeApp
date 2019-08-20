package io.rainrobot.wake.android;

import android.os.Bundle;



import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


import io.rainrobot.wake.android.configuration.WakeActivity;
import io.rainrobot.wake.core.Account;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Device;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.core.Sound;

public class WatchAnyActivity extends WakeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        AndroidEventListView view = new AndroidEventListView(getContextMgr());
////        ControllerMgr controllerMgr = Mockito.mock(ControllerMgr.class);
////        EventListModel model = Mockito.mock(EventListModel.class);
////
////        final int ID = 0;
////        Mockito.when(model.getAllEvents(ID)).thenReturn(createResponse(ID));
//        EventListController controller
//                = new EventListController(view,
//                                            model,
//                                            controllerMgr,
//                                            new AndroidASyncProvider());
//
//        controller.show(ID);
    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        String prefString = "test";
//        SharedPreferences pref = getSharedPreferences(prefString, MODE_PRIVATE);
//        AppContainer container = new AppContainer();
//        ModelFactory modelFactory = Mockito.mock(ModelFactory.class);
//        EventListModel model = Mockito.mock(EventListModel.class);
//        Mockito.when(modelFactory.getEventList()).thenReturn(model);
//        final int ID = 0;
//        Mockito.when(model.getAllEvents(ID)).thenReturn(createResponse(ID));
//        container.setModelFactory(modelFactory);
//
//        AndroidAppConfiguration config
//                = new AndroidAppConfiguration(container,
//                pref,
//                getContextMgr());
//
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_event_list;
//    }

    @Override
    protected int getLayoutId() {
        return NO_VIEW_CONTENT_ID;
    }
    private List<AlarmEvent> createResponse(int ID) {
        return Arrays.asList(createList());
    }

    private AlarmEvent[] createList() {
        int size = 2;
        AlarmEvent[] list = new AlarmEvent[size];
        Account account = new Account(1, "test");
        Preset preset = new Preset(account,
                "ps",
                Calendar.getInstance().getTime(),
                true);
        preset.setId(1);
        for (int i = 0; i < 2; i++) {

            Device device = new Device();
            device.setName("dv" + i);
            int delay = i;
            Sound sound = Sound.values()[i];
            int vol = i * 10;
            int snooze = i + 5;
            int stop = i + 10;

            list[i] = new AlarmEvent(i,
                    preset,
                    device,
                    delay,
                    sound,
                    vol,
                    snooze,
                    stop);
        }
        return list;
    }
}
