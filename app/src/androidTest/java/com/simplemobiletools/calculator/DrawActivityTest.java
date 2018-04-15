package com.simplemobiletools.calculator;

import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.rule.ActivityTestRule;

import com.simplemobiletools.calculator.activities.DrawActivity;
import com.simplemobiletools.calculator.helpers.BackgroundGetGraphTaskBuilder;
import com.simplemobiletools.calculator.views.SketchSheetView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DrawActivityTest {
    @Rule
    public final ActivityTestRule<DrawActivity> activity = new ActivityTestRule<>(DrawActivity.class);

    @Test
    public void clearButtonTest() {
        SketchSheetView mockSketchView = mock(SketchSheetView.class);
        activity.getActivity().supersedeSketchSheetView(mockSketchView);
        press(R.id.btn_draw_clear);
        verify(mockSketchView).clearSketch();
    }

    @Test
    public void testSuccess(){
        BackgroundGetGraphTaskBuilder backgroundGetGraphTaskBuilder = new BackgroundGetGraphTaskBuilder(activity.getActivity());
        BackgroundGetGraphTaskBuilder spyBuilder = spy(backgroundGetGraphTaskBuilder);
        activity.getActivity().supersedeBuilder(spyBuilder);
        when(spyBuilder.build()).thenReturn(new FakeGraphTask(activity.getActivity(),200));
        press(R.id.btn_draw_send);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onWebView().withElement(findElement(Locator.ID, "test")).check(webMatches(getText(), containsString("TEST PAGE")));
    }

    @Test
    public void testPageNotFound(){
        BackgroundGetGraphTaskBuilder backgroundGetGraphTaskBuilder = new BackgroundGetGraphTaskBuilder(activity.getActivity());
        BackgroundGetGraphTaskBuilder spyBuilder = spy(backgroundGetGraphTaskBuilder);
        activity.getActivity().supersedeBuilder(spyBuilder);
        when(spyBuilder.build()).thenReturn(new FakeGraphTask(activity.getActivity(),400));
        onView(withId(R.id.draw_layout)).perform(swipeDown());
        ArrayList<ArrayList<int[]>> strokesBefore =  activity.getActivity().getEquationSketchView().getPoints();
        press(R.id.btn_draw_send);
        onView(withText("OK")).perform(click());
        ArrayList<ArrayList<int[]>> strokesAfter =  activity.getActivity().getEquationSketchView().getPoints();
        assertEquals(strokesBefore,strokesAfter);
    }

    private void press(int id) {
        onView(withId(id)).perform(click());
    }
}

