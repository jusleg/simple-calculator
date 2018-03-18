package com.simplemobiletools.calculator;

import android.content.Context;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.v4.content.PermissionChecker;
import android.util.Log;

import com.simplemobiletools.calculator.activities.MoneyActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@SdkSuppress(minSdkVersion = 18)
@RunWith(AndroidJUnit4.class)
public class MoneyActivityTest {
    @Rule public final ActivityTestRule<MoneyActivity> activity = new ActivityTestRule<>(MoneyActivity.class);

    private Context myContext;
    private MoneyActivity moneyActivity = activity.getActivity();

    @Before
    public void setup() {
        myContext = InstrumentationRegistry.getContext();
    }

    @Test
    public void addDigits() {
        denyLocationPermission();
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
        denyLocationPermission();
        press(R.id.btn_0);
        press(R.id.btn_5);
        checkResult("5.00");
    }

    @Test
    public void clearTest() {
        denyLocationPermission();
        press(R.id.btn_1);
        press(R.id.btn_2);
        longPress((R.id.btn_delete));
        checkResult("0.00");
    }

    @Test
    public void deleteTest() {
        denyLocationPermission();
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
        denyLocationPermission();
        longPress((R.id.btn_delete));
        checkResult("0.00");
        press(R.id.btn_delete);
        checkResult("0.00");
    }


    @Test
    public void taxWithoutGeolocationTest(){
        String locationPermission = "ACCESS_FINE_LOCATION";
        int permission = PermissionChecker.checkSelfPermission(myContext, locationPermission);

        if (Build.VERSION.SDK_INT < 23) {
            if (permission == PermissionChecker.PERMISSION_GRANTED) {
                //if the Android API running is lower than 23 then there is no way to spawn the Province Selection dialog
                //since the location service will always be granted upon installing the app.
                press(R.id.btn_2);
                press(R.id.btn_3);
                //spawning the Province Selector dialog directly
                moneyActivity.spawnTaxModal();
                onView(withId(R.id.province_selector_tax)).perform(click());
                checkResult("25.99");
            }
        } else {
            //otherwise if Android API is =>23 then Deny access to location service to open the Province Selection dialog
            denyLocationPermission();
            press(R.id.btn_2);
            press(R.id.btn_3);
            press(R.id.btn_taxes);
            onView(withId(R.id.province_selector_tax)).perform(click());
            checkResult("25.99");
        }
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

    private static void denyLocationPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            UiDevice device = UiDevice.getInstance(getInstrumentation());
            UiObject denyPermissions = device.findObject(new UiSelector().text("DENY"));
            if (denyPermissions.exists()) {
                try {
                    denyPermissions.click();
                } catch (UiObjectNotFoundException e) {
                    Log.e("PermissionDialog: ", "There is no permissions dialog to interact with ");
                }
            }
        }
    }


}
