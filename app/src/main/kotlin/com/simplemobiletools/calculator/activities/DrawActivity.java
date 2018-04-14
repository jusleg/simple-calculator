package com.simplemobiletools.calculator.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;

import com.simplemobiletools.calculator.R;
import com.simplemobiletools.calculator.helpers.BackgroundCurrencyTaskBuilder;
import com.simplemobiletools.calculator.helpers.BackgroundGetGraphTaskBuilder;
import com.simplemobiletools.calculator.helpers.GetGraphTask;
import com.simplemobiletools.calculator.views.SketchSheetView;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

public class DrawActivity extends AppCompatActivity {

    private SketchSheetView equationSketchView;
    private BackgroundGetGraphTaskBuilder builder = new BackgroundGetGraphTaskBuilder(this);

    public SketchSheetView getEquationSketchView() {
        return equationSketchView;
    }

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

        final DrawActivity activity = this;

        findViewById(R.id.btn_draw_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do web request
                  builder.addStrokes(equationSketchView.exportScgink()).build().execute();
                }
        });

        findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                toggleWebView(null);
            }
        });
    }

    @TestOnly
    public void supersedeSketchSheetView(SketchSheetView equationSketchView) {
        this.equationSketchView = equationSketchView;
    }

    public void recoverAfterError(String error,String errorMessage){
        findViewById(R.id.loading).setVisibility(View.GONE);
        AlertDialog alertDialog = new AlertDialog.Builder(DrawActivity.this,R.style.MyDialogTheme).create();
        alertDialog.setTitle(error);
        alertDialog.setMessage(errorMessage);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void supersedeBuilder(BackgroundGetGraphTaskBuilder builder) {
        this.builder = builder;
    }


    public void toggleWebView(@Nullable String uri) {
        WebView view = findViewById(R.id.wolfram_view);
        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setAppCacheEnabled(true);

        view.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                //hide loading image
                LinearLayout done = findViewById(R.id.done_ui);
                findViewById(R.id.loading).setVisibility(View.GONE);
                view.setVisibility(View.VISIBLE);
                done.setVisibility(View.VISIBLE);
            }

        });
        LinearLayout buttons = findViewById(R.id.draw_ui);
        RelativeLayout canvas = findViewById(R.id.draw_layout);
        LinearLayout done = findViewById(R.id.done_ui);
        if (view.getVisibility() == View.GONE && uri != null){
            WebView loading = findViewById(R.id.loading);
            loading.setVisibility(View.VISIBLE);
            loading.setWebViewClient(new WebViewClient());
            loading.loadUrl("file:///android_asset/batman.html");
            buttons.setVisibility(View.GONE);
            canvas.setVisibility(View.GONE);
            view.loadUrl(uri);

        }
        else {
            view.setVisibility(View.GONE);
            buttons.setVisibility(View.VISIBLE);
            canvas.setVisibility(View.VISIBLE);
            done.setVisibility(View.GONE);
            equationSketchView.clearSketch();
            view.invalidate();
        }
    }
}