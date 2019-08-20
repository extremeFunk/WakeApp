package io.rainrobot.wake.android.view.login;

import io.rainrobot.wake.core.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import io.rainrobot.wake.android.activities.LoginActivity;
import io.rainrobot.wake.android.activities.R;
import io.rainrobot.wake.app.ControllerMgr;
import io.rainrobot.wake.controller.LoginController;
import io.rainrobot.wake.model.LoginModel;
import io.rainrobot.wake.view.LoginView;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    private LoginController controller;
    private LoginView view;
    private LoginModel model;
    private ControllerMgr controllerMgr;
    private String validUsrNm = "john";
    private String validPass = "abc1234";
    private String invalidUsrNm = "false";
    private String invalidPass = "false";

    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        view = new AndroidLoginView(rule.getActivity().getWakeApplication().getContextMgr());
        model = mock(LoginModel.class);
        controllerMgr = mock(ControllerMgr.class);

//        controller = new LoginController(view,
//                                            model,
//                                            controllerMgr,
//                                            new AndroidASyncProvider());
        //start setup the UI, do not delete
        controller.show();

        doThrow(HttpStatusCodeException.class).when(model)
                .login(not(eq(validUsrNm)),not(eq(validPass)),anyBoolean());

    }

    @NonNull
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

    @Test
    public void btnSingupClick() {
        onView(withId(R.id.LoginBtnSingup)).perform(ViewActions.click());
        verify(controllerMgr).showSingup();

    }

    @Test
    public void validUsrAndPassLogin() {
        when(model.isDeviceRegister()).thenReturn(true);

        setUsernameToUi(validUsrNm);
        setPasswordToUi(validPass);
        clickLoginOnUi();

        verify(controllerMgr, times(1)).showMainMenu();
    }

    @Test
    public void invalidUserAndPassLogin() {
        setUsernameToUi(invalidUsrNm);
        setPasswordToUi(validPass);
        clickLoginOnUi();
        assertMsgBoxNotEmpty();

        setUsernameToUi(validUsrNm);
        setPasswordToUi(invalidPass);
        clickLoginOnUi();
        assertMsgBoxNotEmpty();
    }

    private void assertMsgBoxNotEmpty() {
        onView(withId(R.id.txtMsgLogin))
                .check(ViewAssertions.matches(withText("")));
    }

    private void clickLoginOnUi() {
        onView(withId(R.id.LoginBtnLogin)).perform(ViewActions.click());
    }

    private void setUsernameToUi(String string) {
        onView(withId(R.id.enterNewPasswordEditTxt)).perform(ViewActions.typeText(string));
    }

    private void setPasswordToUi(String string) {
        onView(withId(R.id.loginTxtBoxPassword)).perform(ViewActions.typeText(string));
    }

}