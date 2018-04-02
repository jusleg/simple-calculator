package com.simplemobiletools.calculator;

import android.support.test.rule.ActivityTestRule;

import com.simplemobiletools.calculator.activities.DrawActivity;
import com.simplemobiletools.calculator.views.SketchSheetView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.*;

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

    private void press(int id) {
        onView(withId(id)).perform(click());
    }
}

