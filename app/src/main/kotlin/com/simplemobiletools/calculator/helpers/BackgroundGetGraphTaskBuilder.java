package com.simplemobiletools.calculator.helpers;

import android.os.AsyncTask;

import com.simplemobiletools.calculator.activities.DrawActivity;
import com.simpletools.calculator.commons.helpers.Calculator;

public class BackgroundGetGraphTaskBuilder {
    private String strokes = "SCG_INK\n3\n2\n1 2\n3 4\n2\n5 6\n7 8\n1\n9 10\n";
    private DrawActivity drawActivity;

    public BackgroundGetGraphTaskBuilder(DrawActivity drawActivity) {
        this.drawActivity = drawActivity;
    }

    public BackgroundGetGraphTaskBuilder addStrokes(String strokes) {
        this.strokes = strokes;
        return this;
    }

    public AsyncTask<Void, Void, String> build() {
        return new GetGraphTask(strokes , drawActivity);
    }
}