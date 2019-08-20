package io.rainrobot.wake.app;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.runner.AndroidJUnit4;
import io.rainrobot.wake.android.activities.LoginActivity;
import io.rainrobot.wake.android.activities.MainMenuActivity;
import io.rainrobot.wake.android.activities.R;
import io.rainrobot.wake.android.configuration.AndroidAppRunner;
import io.rainrobot.wake.android.configuration.ContextMgr;
import io.rainrobot.wake.android.configuration.WakeActivity;
import io.rainrobot.wake.activitis.EmptyTestActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4.class)
public class AndroidAppBuilderTest {
    @Rule
    public IntentsTestRule<EmptyTestActivity> rule = new IntentsTestRule<>(EmptyTestActivity.class);

    private LoginMgr mockLogMgr;
    private ContextMgr contextMgr;
    private WakeActivity activity;
    private SharedPreferences pref;
    private AppContainer container;


    @Before
    public void setUp() throws Exception {

        activity = rule.getActivity();
        contextMgr = activity.getWakeApplication().getContextMgr();
        pref = activity.getSharedPreferences(activity.getString(R.string.preferences), Context.MODE_PRIVATE);

        //mock login mgr
        mockLogMgr = Mockito.mock(LoginMgr.class);

        container = new AppContainer();
        container.setLoginMgr(mockLogMgr);
    }

    @Test
    public void showMainMenuWithRMeAndTknTrue() {
        Mockito.when(mockLogMgr.isRememberMe()).thenReturn(true);
        Mockito.when(mockLogMgr.isTokenValid()).thenReturn(true);

//        AndroidAppRunner.runWith(contextMgr, pref, container);
        //test if main menu activity been lunched
        intended(hasComponent(MainMenuActivity.class.getName()));
    }

    @Test
    public void showLoginWithRMeFalseAndTknTrue() {
        Mockito.when(mockLogMgr.isRememberMe()).thenReturn(false);
        Mockito.when(mockLogMgr.isTokenValid()).thenReturn(true);

//        AndroidAppRunner.runWith(contextMgr, pref, container);
        //test if main menu activity been lunched
        intended(hasComponent(LoginActivity.class.getName()));
    }

    @Test
    public void showLoginWithRMeFalseAndTknFalse() {
        Mockito.when(mockLogMgr.isRememberMe()).thenReturn(false);
        Mockito.when(mockLogMgr.isTokenValid()).thenReturn(false);

//        AndroidAppRunner.runWith(contextMgr, pref, container);
        //test if main menu activity been lunched
        intended(hasComponent(LoginActivity.class.getName()));
    }

    @Test
    public void showLoginWithRMeTrueAndTknFalse() {
        Mockito.when(mockLogMgr.isRememberMe()).thenReturn(true);
        Mockito.when(mockLogMgr.isTokenValid()).thenReturn(false);

//        AndroidAppRunner.runWith(contextMgr, pref, container);
        //test if main menu activity been lunched
        intended(hasComponent(LoginActivity.class.getName()));
    }
}