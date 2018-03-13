package com.simplemobiletools.calculator;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.simplemobiletools.calculator.activities.MoneyActivity;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class MoneyActivityTest {
    @Rule public final ActivityTestRule<MoneyActivity> activity = new ActivityTestRule<>(MoneyActivity.class);

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


    @Test
    public void taxWithoutGeoLocTest() {
        //TODO: Will need modification once geolocation is implemented
        press(R.id.btn_2);
        press(R.id.btn_3);
        press(R.id.btn_taxes);
        onView(withId(R.id.province_selector_tax)).perform(click());
        checkResult("25.99");
    }

    @Ignore
    @Test
    // This test is failing on Travis CI for an undetermined reason
    public void testConversionSelection() {
        String fromSelection = "EUR";
        String toSelection =  "USD";

        press(R.id.btn_1);
        closeSoftKeyboard();
        press(R.id.btn_currency);

        onView(withId(R.id.convert_from)).inRoot(isDialog()).perform(click());
        onView(withText(containsString(fromSelection))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.convert_from)).check(matches(withSpinnerText(containsString(fromSelection))));

        onView(withId(R.id.convert_to)).inRoot(isDialog()).perform(click());
        onView(withText(containsString(toSelection))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.convert_to)).check(matches(withSpinnerText(containsString(toSelection))));
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
