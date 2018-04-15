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

    public void clickSpinner(String name) {
        onData(allOf(is(instanceOf(String.class)),
                is(name)))
                .inRoot(isPlatformPopup())
                .perform(click());
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
        checkResult("1234567890");
    }

    @Test
    public void removeLeadingZero() {
        press(R.id.btn_0);
        press(R.id.btn_5);
        checkResult("5");
    }

    @Test
    public void clearTest() {
        press(R.id.btn_1);
        press(R.id.btn_2);
        longPress((R.id.btn_delete));
        checkResult("0");
    }

    @Test
    public void deleteTest() {
        press(R.id.btn_1);
        press(R.id.btn_decimal);
        press(R.id.btn_2);
        checkResult("1.2");
        press(R.id.btn_delete);
        checkResult("1");
        press(R.id.btn_3);
        checkResult("13");
        press(R.id.btn_delete);
        press(R.id.btn_delete);
        checkResult("0");
    }

    @Test
    public void deleteClearOnEmptyNumberTest() {
        longPress((R.id.btn_delete));
        checkResult("0");
        press(R.id.btn_delete);
        checkResult("0");
    }

    // --------------------------- WEIGHT CONVERSION TESTS ---------------------------

    @Test
    public void testWeightDialogDisplayed() {
        press(R.id.btn_weight);
        onView(withText("Weight Conversion")).check(matches(isDisplayed()));
    }

    @Test
    public void convertGramToKgdBacktoGram() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_weight);
        press(R.id.from_weight_spinner);
        clickSpinner("kg");
        pressWithText("Convert");
        checkResult("100000.0");
        press(R.id.btn_weight);
        press(R.id.to_weight_spinner);
        clickSpinner("kg");
        pressWithText("Convert");
        checkResult("100.0");
    }

    @Test
    public void convertOzToLbdBacktoOz() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);

        press(R.id.btn_weight);
        press(R.id.from_weight_spinner);
        clickSpinner("oz");
        press(R.id.to_weight_spinner);
        clickSpinner("lb");
        pressWithText("Convert");
        checkResult("6.25");

        press(R.id.btn_weight);
        press(R.id.from_weight_spinner);
        clickSpinner("lb");
        press(R.id.to_weight_spinner);
        clickSpinner("oz");
        pressWithText("Convert");
        checkResult("100.0");
    }

    @Test
    public void convertGramToLb() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);

        press(R.id.btn_weight);
        press(R.id.to_weight_spinner);
        clickSpinner("lb");
        pressWithText("Convert");
        checkResult("0.220462");
    }

    @Test
    public void convertKgToOz() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);

        press(R.id.btn_weight);
        press(R.id.from_weight_spinner);
        clickSpinner("kg");
        press(R.id.to_weight_spinner);
        clickSpinner("oz");
        pressWithText("Convert");
        checkResult("3527.4");
    }

    @Test
    public void convertLbToKg() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);

        press(R.id.btn_weight);
        press(R.id.from_weight_spinner);
        clickSpinner("lb");
        press(R.id.to_weight_spinner);
        clickSpinner("kg");
        pressWithText("Convert");
        checkResult("45.3592");
    }

    @Test
    public void convertOzToGram() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);

        press(R.id.btn_weight);
        press(R.id.from_weight_spinner);
        clickSpinner("oz");
        pressWithText("Convert");
        checkResult("2834.95");
    }

    // --------------------------- LENGTH CONVERSION TESTS ---------------------------

    @Test
    public void testLengthDialogDisplayed() {
        press(R.id.btn_length);
        onView(withText("Length Conversion")).check(matches(isDisplayed()));
    }

    @Test
    public void convertcmToMetersdBacktocm() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);

        press(R.id.btn_length);
        press(R.id.from_length_spinner);
        clickSpinner("m");
        pressWithText("Convert");
        checkResult("10000.0");

        press(R.id.btn_length);
        press(R.id.to_length_spinner);
        clickSpinner("m");
        pressWithText("Convert");
        checkResult("100.0");
    }

    @Test
    public void convertFtToIndBacktoFt() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);

        press(R.id.btn_length);
        press(R.id.from_length_spinner);
        clickSpinner("ft");
        press(R.id.to_length_spinner);
        clickSpinner("in");
        pressWithText("Convert");
        checkResult("1200.0");

        press(R.id.btn_length);
        press(R.id.from_length_spinner);
        clickSpinner("in");
        press(R.id.to_length_spinner);
        clickSpinner("ft");
        pressWithText("Convert");
        checkResult("99.99996");
    }

    @Test
    public void convertCmToIn() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);

        press(R.id.btn_length);
        press(R.id.to_length_spinner);
        clickSpinner("in");
        pressWithText("Convert");
        checkResult("39.3701");
    }

    @Test
    public void convertMToFt() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);

        press(R.id.btn_length);

        press(R.id.from_length_spinner);
        clickSpinner("m");
        press(R.id.to_length_spinner);
        clickSpinner("ft");
        pressWithText("Convert");
        checkResult("328.084");
    }

    @Test
    public void convertInToM() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);

        press(R.id.btn_length);

        press(R.id.from_length_spinner);
        clickSpinner("in");
        press(R.id.to_length_spinner);
        clickSpinner("m");

        pressWithText("Convert");
        checkResult("2.54");
    }

    @Test
    public void convertFtToCm() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);

        press(R.id.btn_length);

        press(R.id.from_length_spinner);
        clickSpinner("ft");
        press(R.id.to_length_spinner);
        clickSpinner("cm");

        pressWithText("Convert");
        checkResult("3048.0");
    }

    // --------------------------- VOLUME CONVERSION TESTS ---------------------------

    @Test
    public void testVolumeDialogDisplayed() {
        press(R.id.btn_volume);
        onView(withText("Volume Conversion")).check(matches(isDisplayed()));
    }

    @Test
    public void convertLToMlBacktoL() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);

        press(R.id.btn_volume);
        press(R.id.from_volume_spinner);
        clickSpinner("L");
        pressWithText("Convert");
        checkResult("100000.0");

        press(R.id.btn_volume);
        press(R.id.to_volume_spinner);
        clickSpinner("L");
        pressWithText("Convert");
        checkResult("100.0");
    }

    @Test
    public void convertGallonToQtdBacktoGallon() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);

        press(R.id.btn_volume);
        press(R.id.from_volume_spinner);
        clickSpinner("Gallon");
        press(R.id.to_volume_spinner);
        clickSpinner("Quart");
        pressWithText("Convert");
        checkResult("400.0");

        press(R.id.btn_volume);
        press(R.id.from_volume_spinner);
        clickSpinner("Quart");
        press(R.id.to_volume_spinner);
        clickSpinner("Gallon");
        pressWithText("Convert");
        checkResult("100.0");
    }

    @Test
    public void convertMlToQuart() {
        press(R.id.btn_1);

        press(R.id.btn_volume);
        press(R.id.to_volume_spinner);
        clickSpinner("Quart");
        pressWithText("Convert");
        checkResult("0.00105669");
    }

    @Test
    public void convertLToGallon() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);

        press(R.id.btn_volume);

        press(R.id.from_volume_spinner);
        clickSpinner("L");
        press(R.id.to_volume_spinner);
        clickSpinner("Gallon");
        pressWithText("Convert");
        checkResult("26.4172");
    }

    @Test
    public void convertQuartToLiter() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);

        press(R.id.btn_volume);

        press(R.id.from_volume_spinner);
        clickSpinner("Quart");
        press(R.id.to_volume_spinner);
        clickSpinner("L");

        pressWithText("Convert");
        checkResult("94.6353");
    }

    @Test
    public void convertGallonToMl() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);

        press(R.id.btn_volume);

        press(R.id.from_volume_spinner);
        clickSpinner("Gallon");
        press(R.id.to_volume_spinner);
        clickSpinner("Ml");

        pressWithText("Convert");
        checkResult("378541.0");
    }

}
