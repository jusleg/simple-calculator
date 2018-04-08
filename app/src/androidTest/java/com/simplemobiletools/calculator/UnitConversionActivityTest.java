package com.simplemobiletools.calculator;

import android.content.Context;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.v4.content.PermissionChecker;
import android.util.Log;

import com.simplemobiletools.calculator.activities.UnitConversionActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsString;

@SdkSuppress(minSdkVersion = 18)
public class UnitConversionActivityTest {
    @Rule public final ActivityTestRule<UnitConversionActivity> activity = new ActivityTestRule<>(UnitConversionActivity.class);

    private Context myContext;
    private UnitConversionActivity conversionActivity = activity.getActivity();

    @Before
    public void setup() {
        myContext = InstrumentationRegistry.getContext();
    }

    private void press(int id) {
        onView(withId(id)).perform(click());
    }

    private void pressWithText(String text) {
        onView(withText(text)).perform(click());
    }

    private void longPress(int id) {
        onView(withId(id)).perform(longClick());
    }

    private void checkResult(String desired) {
        onView(withId(R.id.result)).check(matches(withText(desired)));
    }

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
    public void testWeightDialogDisplayed() {
        press(R.id.btn_weight);
        onView(withText("Weight Conversion")).check(matches(isDisplayed()));
    }

    @Test
    public void convertGramToGramWeight() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_weight);
        pressWithText("Convert");
        checkResult("100.00");
    }

    @Test
    public void convertGramToKg() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_weight);
        press(R.id.from_weight_spinner);
        onData(allOf(is(instanceOf(String.class)),
                is("kg")))
                .inRoot(isPlatformPopup())
                .perform(click());
        pressWithText("Convert");
        checkResult("100,000.00");
    }
}
