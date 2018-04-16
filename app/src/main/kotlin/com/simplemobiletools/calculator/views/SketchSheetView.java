package com.simplemobiletools.calculator.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import org.jetbrains.annotations.TestOnly;

import java.util.ArrayList;

public class SketchSheetView extends View {

    private Paint paint = new Paint();
    private Path path = new Path();
    private Canvas canvas = new Canvas(Bitmap.createBitmap(820, 480, Bitmap.Config.ARGB_4444));
    private ArrayList<ArrayList<int[]>> points = new ArrayList<>();
    private DrawingClass pathWithPaint = new DrawingClass();

    public SketchSheetView(Context context) {
        this(context, Color.BLACK, Color.WHITE);
    }

    public SketchSheetView(Context context, int lineColor, int backgroundColor) {
        super(context);
        this.setBackgroundColor(backgroundColor);
        paint.setDither(true);
        paint.setColor(lineColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(4);
        pathWithPaint.setPath(path);
        pathWithPaint.setPaint(paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        canvas.drawPath(path, paint);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startNewStroke();
            addPoint(Math.round(event.getX()), Math.round(event.getY()));
            path.moveTo(event.getX(), event.getY());
            path.lineTo(event.getX()-1, event.getY()-1);
            path.lineTo(event.getX(), event.getY());
        }
        else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            addPoint(Math.round(event.getX()), Math.round(event.getY()));
            path.lineTo(event.getX(), event.getY());
        }

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(pathWithPaint.getPath(), pathWithPaint.getPaint());
    }

    public void addPoint(int x, int y){
        x = (x < 0) ? 0 : x;
        y = (y < 0) ? 0 : y;

        int[] position = {x,y};
        points.get(points.size() - 1).add(position);
    }

    public void startNewStroke() {
        points.add(new ArrayList<int[]>());
    }

    public String exportScgink() {
        String output = "SCG_INK\n" + points.size() + "\n";
        for (int i = 0; i < points.size(); i++) {
            output += points.get(i).size() + "\n";
            for(int k = 0; k < points.get(i).size(); k++) {
                int[] coordinates = points.get(i).get(k);
                output += coordinates[0] + " " + coordinates[1] + "\n";
            }
        }
        return output;
    }

    public void clearSketch() {
        path.reset();
        points = new ArrayList<>();
        invalidate();
    }

    @TestOnly
    public ArrayList<ArrayList<int[]>> getPoints() {
        return points;
    }

    class DrawingClass {

        Path DrawingClassPath;
        Paint DrawingClassPaint;

        public Path getPath() {
            return DrawingClassPath;
        }

        public void setPath(Path path) {
            this.DrawingClassPath = path;
        }


        public Paint getPaint() {
            return DrawingClassPaint;
        }

        public void setPaint(Paint paint) {
            this.DrawingClassPaint = paint;
        }
    }
}