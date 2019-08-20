package io.rainrobot.wake;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import io.rainrobot.wake.activitis.EmptyTestActivity;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import io.rainrobot.wake.android.activities.R;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class InstTestTamplet {
    @Rule
    public IntentsTestRule<EmptyTestActivity> rule = new IntentsTestRule<>(EmptyTestActivity.class);
    protected Activity activity;
    protected SharedPreferences sharedPreferences;
    @Before
    public void setUp() throws Exception{
        activity = rule.getActivity();
        String name = activity.getString(R.string.preferences);
        sharedPreferences = activity.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

}
