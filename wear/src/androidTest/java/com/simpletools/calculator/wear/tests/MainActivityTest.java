
package com.simpletools.calculator.wear.tests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.simpletools.calculator.wear.MainActivity;
import com.simpletools.calculator.wear.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testCalc() {
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
        checkResult("1,234,567,890");
    }

    @Test
    public void removeLeadingZero() {
        press(R.id.btn_0);
        press(R.id.btn_5);
        checkResult("5");
    }

    @Test
    public void addTest(){
        press(R.id.btn_1);
        pressOperation("+");
        press(R.id.btn_2);
        press(R.id.btn_equals);
        checkResult("3");
    }

    @Test
    public void additionTest() {
        press(R.id.btn_2);
        press(R.id.btn_decimal);
        press(R.id.btn_2);
        pressOperation("+");
        press(R.id.btn_6);
        press(R.id.btn_equals);
        checkResult("8.2");
    }

    @Test
    public void subtractionTest() {
        press(R.id.btn_7);
        press(R.id.btn_decimal);
        press(R.id.btn_2);
        pressOperation("−");
        press(R.id.btn_3);
        press(R.id.btn_equals);
        checkResult("4.2");
    }

    @Test
    public void multiplyTest() {
        press(R.id.btn_2);
        pressOperation("×");
        press(R.id.btn_4);
        press(R.id.btn_equals);
        checkResult("8");
    }

    @Test
    public void divisionTest() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        pressOperation("÷");
        press(R.id.btn_4);
        press(R.id.btn_equals);
        checkResult("2.5");
    }

    @Test
    public void divisionByZeroTest() {
        press(R.id.btn_2);
        pressOperation("÷");
        press(R.id.btn_0);
        press(R.id.btn_equals);
        checkResult("∞");
    }

    @Test
    public void clearTest() {
        press(R.id.btn_1);
        pressOperation("+");
        press(R.id.btn_2);
        press(R.id.btn_clear);
        onView(withId(R.id.btn_clear)).check(matches(withText("AC")));
        press(R.id.btn_3);
        onView(withId(R.id.btn_clear)).check(matches(withText("CE")));
        press(R.id.btn_equals);
        checkResult("4");
    }

    @Test
    public void clearLongTest() {
        press(R.id.btn_2);
        pressOperation("+");
        press(R.id.btn_5);
        press(R.id.btn_equals);
        longPress((R.id.btn_clear));
        pressOperation("+");
        press(R.id.btn_2);
        press(R.id.btn_equals);
        checkResult("2");
    }

    @Test
    public void complexTest() {
        press(R.id.btn_2);
        pressOperation("+");
        press(R.id.btn_2);
        pressOperation("−");
        checkResult("4");

        press(R.id.btn_3);
        pressOperation("×");
        checkResult("1");

        press(R.id.btn_2);
        pressOperation("÷");
        checkResult("2");

        press(R.id.btn_2);
        pressOperation("×");
        checkResult("1");

        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_equals);
        checkResult("10");

        press(R.id.btn_clear);
        checkResult("0");
    }

    private void pressOperation(String operation){
        onView(withId(R.id.wear_holder)).perform(swipeDown());
        onView(withText(operation)).perform(click());
    }

    private void longPress(int id) {
        onView(withId(id)).perform(longClick());
    }

    private void press(int id) {
        onView(withId(id)).perform(click());
    }

    private void checkResult(String desired) {
        onView(withId(R.id.result)).check(matches(withText(desired)));
    }

}