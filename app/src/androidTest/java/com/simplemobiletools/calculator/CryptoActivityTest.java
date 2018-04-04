package com.simplemobiletools.calculator;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.simplemobiletools.calculator.activities.CryptoActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CryptoActivityTest {
    @Rule
    public final ActivityTestRule<CryptoActivity> activity = new ActivityTestRule<>(CryptoActivity.class);

    @Test
    public void addDigits() {
        press(R.id.btn_1);
        press(R.id.btn_2);
        press(R.id.btn_3);
        press(R.id.btn_4);
        press(R.id.btn_5);
        press(R.id.btn_6);
        press(R.id.btn_7);
        press(R.id.btn_8);
        press(R.id.btn_9);
        press(R.id.btn_0);
        checkResult("1,234,567,890.00");
    }

    @Test
    public void removeLeadingZero() {
        press(R.id.btn_0);
        press(R.id.btn_5);
        checkResult("5.00");
    }

    @Test
    public void clearTest() {
        press(R.id.btn_1);
        press(R.id.btn_2);
        longPress((R.id.btn_delete));
        checkResult("0.00");
    }

    @Test
    public void deleteTest() {
        press(R.id.btn_1);
        press(R.id.btn_decimal);
        press(R.id.btn_2);
        checkResult("1.20");
        press(R.id.btn_delete);
        checkResult("1.00");
        press(R.id.btn_3);
        checkResult("13.00");
        press(R.id.btn_delete);
        press(R.id.btn_delete);
        checkResult("0.00");
    }

    @Test
    public void deleteClearOnEmptyNumberTest() {
        longPress((R.id.btn_delete));
        checkResult("0.00");
        press(R.id.btn_delete);
        checkResult("0.00");
    }

    private void press(int id) {
        onView(withId(id)).perform(click());
    }

    private void longPress(int id) {
        onView(withId(id)).perform(longClick());
    }

    private void checkResult(String desired) {
        onView(withId(R.id.result)).check(matches(withText(desired)));
    }
}