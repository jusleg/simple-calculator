package com.simplemobiletools.calculator;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.simplemobiletools.calculator.activities.BaseActivity;

import org.junit.Ignore;
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
public class BaseActivityTest {
    @Rule
    public final ActivityTestRule<BaseActivity> activity = new ActivityTestRule<>(BaseActivity.class);

    @Test
    public void addDigitsTest() {
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
        checkResult("1234567890");
    }

    @Test
    public void removeLeadingZeroTest() {
        press(R.id.btn_0);
        press(R.id.btn_5);
        checkResult("5");
    }

    @Test
    public void bitwiseDecAndTest() {
        press(R.id.btn_dec);
        press(R.id.btn_1);
        press(R.id.btn_2);
        press(R.id.btn_and);
        press(R.id.btn_2);
        press(R.id.btn_5);
        press(R.id.btn_equals);
        checkResult("8");
        checkFormula("12&25");
    }

    @Test
    public void bitwiseDecOrTest() {
        press(R.id.btn_dec);
        press(R.id.btn_1);
        press(R.id.btn_2);
        press(R.id.btn_or);
        press(R.id.btn_2);
        press(R.id.btn_5);
        press(R.id.btn_equals);
        checkResult("29");
        checkFormula("12|25");
    }

    @Test
    public void bitwiseXorTest() {
        press(R.id.btn_dec);
        press(R.id.btn_1);
        press(R.id.btn_2);
        press(R.id.btn_xor);
        press(R.id.btn_2);
        press(R.id.btn_5);
        press(R.id.btn_equals);
        checkResult("21");
        checkFormula("12^25");
    }

    @Ignore
    @Test
    public void bitwiseInvTest() {
        press(R.id.btn_dec);
        press(R.id.btn_3);
        press(R.id.btn_5);

        press(R.id.btn_equals);
        checkResult("21");
        checkFormula("12^25");
    }

    @Test
    public void clearTest() {
        press(R.id.btn_dec);
        press(R.id.btn_1);
        press(R.id.btn_2);
        press(R.id.btn_and);
        press(R.id.btn_2);
        press(R.id.btn_clear);
        onView(withId(R.id.btn_clear)).check(matches(withText("AC")));
        press(R.id.btn_2);
        press(R.id.btn_5);
        onView(withId(R.id.btn_clear)).check(matches(withText("CE")));
        press(R.id.btn_equals);
        checkResult("8");
    }

    @Test
    public void clearLongTest() {
        press(R.id.btn_2);
        press(R.id.btn_and);
        press(R.id.btn_5);
        press(R.id.btn_equals);
        longPress((R.id.btn_clear));
        press(R.id.btn_plus);
        press(R.id.btn_2);
        press(R.id.btn_equals);
        checkResult("2");
        checkFormula("0+2");
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

    private void checkFormula(String desired) {
        onView(withId(R.id.formula)).check(matches(withText(desired)));
    }
}
