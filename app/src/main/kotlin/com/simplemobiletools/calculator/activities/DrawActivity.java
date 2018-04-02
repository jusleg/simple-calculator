package com.simplemobiletools.calculator.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;
import com.simplemobiletools.calculator.R;
import com.simplemobiletools.calculator.views.SketchSheetView;

import org.jetbrains.annotations.TestOnly;

public class DrawActivity extends AppCompatActivity {

    private SketchSheetView equationSketchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        RelativeLayout relativeLayout = findViewById(R.id.draw_layout);
        equationSketchView = new SketchSheetView(DrawActivity.this);
        relativeLayout.addView(equationSketchView, new LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        findViewById(R.id.btn_draw_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equationSketchView.clearSketch();
            }
        });

        findViewById(R.id.btn_draw_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do web request
            }
        });
    }

    @TestOnly
    public void supersedeSketchSheetView(SketchSheetView equationSketchView) {
        this.equationSketchView = equationSketchView;
    }
}