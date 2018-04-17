package com.simplemobiletools.calculator;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.simplemobiletools.calculator.activities.BitwiseActivity;

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
public class BitwiseActivityTest {
    @Rule
    public final ActivityTestRule<BitwiseActivity> activity = new ActivityTestRule<>(BitwiseActivity.class);

    @Test
    public void addDigitsDecTest() {
        press(R.id.btn_dec);
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
    public void addDigitsOctTest() {
        press(R.id.btn_oct);
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
        checkResult("12345670");
    }

    @Test
    public void addDigitsBinTest() {
        press(R.id.btn_bin);
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
        checkResult("10");
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
    public void bitwiseDecXorTest() {
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

    @Test
    public void bitwiseDecInvTest() {
        press(R.id.btn_dec);
        press(R.id.btn_3);
        press(R.id.btn_5);
        press(R.id.btn_inv);
        checkResult("-36");
        checkFormula("~(35)");
    }

    @Test
    public void bitwiseOctAndTest() {
        press(R.id.btn_oct);
        press(R.id.btn_1);
        press(R.id.btn_2);
        press(R.id.btn_and);
        press(R.id.btn_2);
        press(R.id.btn_5);
        press(R.id.btn_equals);
        checkResult("0");
        checkFormula("12&25");
    }

    @Test
    public void bitwiseOctOrTest() {
        press(R.id.btn_oct);
        press(R.id.btn_1);
        press(R.id.btn_2);
        press(R.id.btn_or);
        press(R.id.btn_2);
        press(R.id.btn_5);
        press(R.id.btn_equals);
        checkResult("37");
        checkFormula("12|25");
    }

    @Test
    public void bitwiseOctXorTest() {
        press(R.id.btn_oct);
        press(R.id.btn_1);
        press(R.id.btn_2);
        press(R.id.btn_xor);
        press(R.id.btn_2);
        press(R.id.btn_5);
        press(R.id.btn_equals);
        checkResult("37");
        checkFormula("12^25");
    }

    @Test
    public void bitwiseOctInvTest() {
        press(R.id.btn_oct);
        press(R.id.btn_3);
        press(R.id.btn_5);
        press(R.id.btn_inv);
        checkResult("-36");
        checkFormula("~(35)");
    }

    @Test
    public void bitwiseBinAndTest() {
        press(R.id.btn_bin);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_and);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_equals);
        checkResult("10");
        checkFormula("10&11");
    }

    @Test
    public void bitwiseBinOrTest() {
        press(R.id.btn_bin);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_or);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_equals);
        checkResult("11");
        checkFormula("10|11");
    }

    @Test
    public void bitwiseBinXorTest() {
        press(R.id.btn_bin);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_xor);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_equals);
        checkResult("1");
        checkFormula("10^11");
    }

    @Test
    public void bitwiseBinInvTest() {
        press(R.id.btn_bin);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_inv);
        checkResult("-100");
        checkFormula("~(11)");
    }

    @Test
    public void conversionDecBinTest() {
        press(R.id.btn_dec);
        press(R.id.btn_9);
        press(R.id.btn_bin);
        checkResult("1001");
    }

    @Test
    public void conversionDecOctTest() {
        press(R.id.btn_dec);
        press(R.id.btn_9);
        press(R.id.btn_oct);
        checkResult("11");
    }

    @Test
    public void conversionOctDecTest() {
        press(R.id.btn_oct);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_dec);
        checkResult("9");
    }

    @Test
    public void conversionOctBinTest() {
        press(R.id.btn_oct);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_bin);
        checkResult("1001");
    }

    @Test
    public void conversionBinDecTest() {
        press(R.id.btn_bin);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_1);
        press(R.id.btn_dec);
        checkResult("9");
    }

    @Test
    public void conversionBinOctTest() {
        press(R.id.btn_bin);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_1);
        press(R.id.btn_oct);
        checkResult("11");
    }

    @Test
    public void conversionWithAfterOperation() {
        press(R.id.btn_dec);
        press(R.id.btn_9);
        press(R.id.btn_and);
        press(R.id.btn_1);
        press(R.id.btn_bin);
        checkResult("0");
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
    public void bigDecimalNumberToBinaryTest() {
        press(R.id.btn_dec);
        press(R.id.btn_9);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_1);
        press(R.id.btn_bin);
        checkResult("10001100101001");
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
