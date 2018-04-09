package com.simplemobiletools.calculator;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.simplemobiletools.calculator.activities.MainActivity;
import com.simpletools.calculator.commons.helpers.Formatter;

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
public class MainActivityTest {
    @Rule public final ActivityTestRule<MainActivity> activity = new ActivityTestRule<>(MainActivity.class);

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
        checkResult("1,234,567,890");
    }

    @Test
    public void removeLeadingZeroTest() {
        press(R.id.btn_0);
        press(R.id.btn_5);
        checkResult("5");
    }

    @Test
    public void removeTrailingZero() {
        press(R.id.btn_1);
        press(R.id.btn_decimal);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_0);
        checkResult("1.000");
        press(R.id.btn_clear);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_decimal);
        press(R.id.btn_0);
        checkResult("1,000.0");
    }

    @Test
    public void additionTest() {
        press(R.id.btn_minus);
        press(R.id.btn_2);
        press(R.id.btn_decimal);
        press(R.id.btn_5);
        press(R.id.btn_plus);
        press(R.id.btn_6);
        press(R.id.btn_equals);
        checkResult("3.5");
        checkFormula("(-2.5)+6");
    }

    @Test
    public void subtractionTest() {
        press(R.id.btn_7);
        press(R.id.btn_decimal);
        press(R.id.btn_8);
        press(R.id.btn_minus);
        press(R.id.btn_3);
        press(R.id.btn_equals);
        checkResult("4.8");
        checkFormula("7.8−3");
    }

    @Test
    public void multiplyTest() {
        press(R.id.btn_2);
        press(R.id.btn_multiply);
        press(R.id.btn_4);
        press(R.id.btn_equals);
        checkResult("8");
        checkFormula("2×4");
    }

    @Test
    public void divisionTest() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_divide);
        press(R.id.btn_4);
        press(R.id.btn_equals);
        checkResult("2.5");
        checkFormula("10÷4");
    }

    @Test
    public void divisionByZeroTest() {
        press(R.id.btn_8);
        press(R.id.btn_divide);
        press(R.id.btn_0);
        press(R.id.btn_equals);
        checkResult("∞");
        checkFormula("8÷0");
    }

    @Test
    public void percentageTest() {
        press(R.id.btn_7);
        press(R.id.btn_percentage);
        checkResult("0.07");
        checkFormula("7%");
    }

    @Test
    public void rootTest() {
        press(R.id.btn_8);
        press(R.id.btn_1);
        press(R.id.btn_root);
        checkResult("9");
        checkFormula("√81");
    }

    @Test
    public void clearTest() {
        press(R.id.btn_1);
        press(R.id.btn_plus);
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
        press(R.id.btn_plus);
        press(R.id.btn_5);
        press(R.id.btn_equals);
        longPress((R.id.btn_clear));
        press(R.id.btn_plus);
        press(R.id.btn_2);
        press(R.id.btn_equals);
        checkResult("2");
        checkFormula("0+2");
    }

    @Test
    public void complexTest() {
        press(R.id.btn_2);
        press(R.id.btn_plus);
        press(R.id.btn_5);
        press(R.id.btn_minus);
        checkResult("7");
        checkFormula("2+5");

        press(R.id.btn_3);
        press(R.id.btn_multiply);
        checkResult("4");
        checkFormula("7−3");

        press(R.id.btn_5);
        press(R.id.btn_divide);
        checkResult("20");
        checkFormula("4×5");

        press(R.id.btn_2);
        press(R.id.btn_multiply);
        checkResult("10");
        checkFormula("20÷2");

        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_equals);
        checkResult("100");
        checkFormula("10×10");

        press(R.id.btn_root);
        checkResult("10");
        checkFormula("√100");


        press(R.id.btn_percentage);
        checkResult("0.1");
        checkFormula("10%");

        press(R.id.btn_clear);
        checkResult("0");
    }

    @Test
    public void negationTest() {
        press(R.id.btn_1);
        press(R.id.btn_negative);
        checkResult("-1");
        press(R.id.btn_negative);
        checkResult("1");
        press(R.id.btn_multiply);
        press(R.id.btn_2);
        press(R.id.btn_negative);
        press(R.id.btn_equals);
        checkResult("-2");
        checkFormula("1×(-2)");
    }

    @Test
    public void squaredTest() {
        press(R.id.btn_2);
        press(R.id.btn_squared);
        checkResult("4");
        checkFormula("2²");
        press(R.id.btn_squared);
        checkResult("16");
        checkFormula("4²");
    }

    @Test
    public void cubedTest() {
        press(R.id.btn_2);
        press(R.id.btn_cubed);
        checkResult("8");
        checkFormula("2³");
        press(R.id.btn_cubed);
        checkResult("512");
        checkFormula("8³");
    }

    @Test
    public void powerTest1() {
        press(R.id.btn_2);
        press(R.id.btn_power);
        press(R.id.btn_5);
        press(R.id.btn_equals);
        checkResult("32");
        checkFormula("2^5");
    }

    @Test
    public void powerTest2() {
        press(R.id.btn_2);
        press(R.id.btn_negative);
        press(R.id.btn_power);
        press(R.id.btn_5);
        press(R.id.btn_equals);
        checkResult("-32");
        checkFormula("(-2)^5");
    }

    @Test
    public void powerTest3() {
        press(R.id.btn_2);
        press(R.id.btn_power);
        press(R.id.btn_5);
        press(R.id.btn_negative);
        press(R.id.btn_equals);
        checkResult("0.03125");
        checkFormula("2^(-5)");
    }

    @Test
    public void powerTest4() {
        press(R.id.btn_2);
        press(R.id.btn_negative);
        press(R.id.btn_power);
        press(R.id.btn_5);
        press(R.id.btn_negative);
        press(R.id.btn_equals);
        checkResult("-0.03125");
        checkFormula("(-2)^(-5)");
    }

    @Test
    public void powerTest5() {
        press(R.id.btn_2);
        press(R.id.btn_negative);
        press(R.id.btn_power);
        press(R.id.btn_decimal);
        press(R.id.btn_5);
        press(R.id.btn_equals);
        checkResult(Formatter.INSTANCE.doubleToString(Double.NaN));
        checkFormula("(-2)^0.5");
    }

    @Test
    public void powerTest6() {
        press(R.id.btn_0);
        press(R.id.btn_power);
        press(R.id.btn_5);
        press(R.id.btn_negative);
        press(R.id.btn_equals);
        checkResult(Formatter.INSTANCE.doubleToString(Double.POSITIVE_INFINITY));
        checkFormula("0^(-5)");
    }

    @Test
    public void moduloTest1() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_mod);
        press(R.id.btn_6);
        press(R.id.btn_equals);
        checkResult("4");
        checkFormula("10mod6");
    }

    @Test
    public void moduloTest2() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_negative);
        press(R.id.btn_mod);
        press(R.id.btn_6);
        press(R.id.btn_equals);
        checkResult("2");
        checkFormula("(-10)mod6");
    }

    @Test
    public void moduloTest3() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_negative);
        press(R.id.btn_mod);
        press(R.id.btn_6);
        press(R.id.btn_negative);
        press(R.id.btn_equals);
        checkResult("2");
        checkFormula("(-10)mod(-6)");
    }

    @Test
    public void moduloTest4() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_mod);
        press(R.id.btn_6);
        press(R.id.btn_negative);
        press(R.id.btn_equals);
        checkResult("4");
        checkFormula("10mod(-6)");
    }

    @Test
    public void moduloTest5() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_mod);
        press(R.id.btn_0);
        press(R.id.btn_equals);
        checkResult("10");
        checkFormula("10mod0");
    }

    @Test
    public void moduloTest6() {
        press(R.id.btn_0);
        press(R.id.btn_mod);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_equals);
        checkResult("0");
        checkFormula("0mod10");
    }

    @Test
    public void negativeThenNumberTest(){
        press(R.id.btn_negative);
        checkResult("-0");
        press(R.id.btn_1);
        checkResult("-1");
        press(R.id.btn_multiply);
        press(R.id.btn_3);
        press(R.id.btn_5);
        press(R.id.btn_equals);
        checkResult("-35");
        checkFormula("(-1)×35");
    }

    @Test
    public void negativeThenNumberDecimalTest(){
        press(R.id.btn_negative);
        checkResult("-0");
        press(R.id.btn_decimal);
        press(R.id.btn_0);
        checkResult("-0.0");
        press(R.id.btn_1);
        checkResult("-0.01");
        press(R.id.btn_multiply);
        press(R.id.btn_3);
        press(R.id.btn_5);
        press(R.id.btn_equals);
        checkResult("-0.35");
        checkFormula("(-0.01)×35");
    }

    @Test
    public void negativeThenNumberChainedEqualsTest() {
        press(R.id.btn_negative);
        press(R.id.btn_5);
        press(R.id.btn_plus);
        press(R.id.btn_2);
        press(R.id.btn_equals);
        checkResult("-3");
        checkFormula("(-5)+2");
        press(R.id.btn_equals);
        checkResult("-1");
        checkFormula("(-3)+2");
        press(R.id.btn_negative);
        press(R.id.btn_equals);
        checkResult("3");
        checkFormula("1+2");
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
