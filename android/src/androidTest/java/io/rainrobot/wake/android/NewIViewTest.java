package io.rainrobot.wake.android;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

import io.rainrobot.wake.android.activities.NewTestEmptyActivity;

@RunWith(AndroidJUnit4.class)
public class NewIViewTest {
    @Rule
    public IntentsTestRule<NewTestEmptyActivity> rule = new IntentsTestRule<>(NewTestEmptyActivity.class);
}
