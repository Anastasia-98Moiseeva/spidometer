package com.example.spidometer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


public class Spidometer extends View {

    private static final float INDENT = 64f;
    private static final int GRADIENT_WIDTH = 40;
    private static final float RADIUS = 500f;
    private static float TEXT_RADIUS = 400f;
    private static final float ARROW_RADIUS = 370f;
    private static final int MAX_SPEED = 160;
    private static final int MIN_SPEED = 0;

    private static int speed = 65;
    private static int arrowColor = Color.DKGRAY;

    private int width;

    private final String txt0 = getResources().getString(R.string.txt0);
    private final String txt20 = getResources().getString(R.string.txt20);
    private final String txt40 = getResources().getString(R.string.txt40);
    private final String txt60 = getResources().getString(R.string.txt60);
    private final String txt80 = getResources().getString(R.string.txt80);
    private final String txt100 = getResources().getString(R.string.txt100);
    private final String txt120 = getResources().getString(R.string.txt120);
    private final String txt140 = getResources().getString(R.string.txt140);
    private final String txt160 = getResources().getString(R.string.txt160);

    private final int[] colors = new int[] {Color.TRANSPARENT,
            getResources().getColor(R.color.colorRed),
            getResources().getColor(R.color.colorOrange),
            getResources().getColor(R.color.colorYellow),
            getResources().getColor(R.color.colorGreen),
            getResources().getColor(R.color.colorBlue),
            getResources().getColor(R.color.colorDarkBlue),
            getResources().getColor(R.color.colorPurple),
            Color.TRANSPARENT};

    private final float[] colorPositions = new float[]{ 0.3f, 0.35f, 0.45f, 0.55f,
            0.65f, 0.75f, 0.85f, 0.95f, 1.0f};

    private int[] centerColors = {Color.TRANSPARENT, Color.LTGRAY, Color.LTGRAY,
            Color.LTGRAY, Color.TRANSPARENT};
    private float[] centerColorPositions = {0.3f, 0.35f, 0.65f, 0.95f, 1.0f};

    private int[] sideRingColors = {Color.TRANSPARENT, Color.BLACK, Color.BLACK,
            Color.BLACK, Color.TRANSPARENT};
    private float[] sideRingPositions = {0.3f, 0.35f, 0.65f, 0.95f, 1.0f};

    public Spidometer(Context context) {
        super(context);
    }

    public Spidometer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(final Canvas canvas){
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(INDENT, INDENT);
        drawGradient(canvas);
        drawCenterRound(canvas);
        drawRing(canvas);
        canvas.restore();
        drawSpeedLines(canvas);
        drawSpeedNumbers(canvas);
        setSpeed(speed, canvas);
    }


    private void drawGradient(Canvas canvas){
        RectF rectF = new RectF(0, 0, width - 2*INDENT, width - 2*INDENT);
        Paint gradientPaint = new Paint();
        SweepGradient gradient = new SweepGradient(width/2 - INDENT, width/2 - INDENT, colors, colorPositions);
        gradientPaint.setShader(gradient);
        gradientPaint.setStrokeWidth(GRADIENT_WIDTH);
        gradientPaint.setAntiAlias(true);
        gradientPaint.setStyle(Paint.Style.STROKE);
        canvas.rotate(37, width/2 - INDENT, width /2 - INDENT);
        canvas.drawArc(rectF, -90f, -360f, false, gradientPaint);
    }


    private void drawCenterRound(Canvas canvas){
        Paint paintCenterRound = new Paint();
        SweepGradient centerGradient = new SweepGradient(width/2 - INDENT, width/2 - INDENT, centerColors, centerColorPositions);
        paintCenterRound.setShader(centerGradient);
        paintCenterRound.setColor(Color.LTGRAY);
        canvas.drawCircle(width/2 - INDENT, width /2 - INDENT, 350f, paintCenterRound);
    }


    private void drawRing(Canvas canvas){
        Paint paintSideRing = new Paint();
        SweepGradient sideRingGradient = new SweepGradient(width/2 - INDENT, width/2 - INDENT, sideRingColors, sideRingPositions);
        paintSideRing.setShader(sideRingGradient);
        paintSideRing.setStrokeWidth(8);
        paintSideRing.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(width/2 - INDENT, width/2 - INDENT, RADIUS, paintSideRing);
    }


    private void drawSpeedLines(Canvas canvas){
        final Paint paintLine = new Paint();
        paintLine.setStrokeWidth(5);
        paintLine.setColor(Color.BLACK);
        canvas.drawLine(width/2 - RADIUS, width/2, INDENT + GRADIENT_WIDTH, width/2, paintLine);
        canvas.drawLine(width/2 + RADIUS, width/2, width - INDENT - GRADIENT_WIDTH, width/2, paintLine);
        canvas.drawLine(width/2, width/2 - RADIUS, width/2, INDENT + GRADIENT_WIDTH, paintLine);
        canvas.drawLine(width/2 - (float)  Math.cos(Math.toRadians(30))* RADIUS,
                width/2 - (float) Math.sin(Math.toRadians(30))* RADIUS,
                width/2 - (float)  Math.cos(Math.toRadians(30))*(RADIUS - INDENT),
                width/2 - (float) Math.sin(Math.toRadians(30))*(RADIUS - INDENT), paintLine);
        canvas.drawLine(width/2 - (float)  Math.cos(Math.toRadians(60))* RADIUS,
                width/2 - (float) Math.sin(Math.toRadians(60))* RADIUS,
                width/2 - (float)  Math.cos(Math.toRadians(60))*(RADIUS - INDENT),
                width/2 - (float) Math.sin(Math.toRadians(60))*(RADIUS - INDENT), paintLine);
        canvas.drawLine(width/2 + (float)  Math.cos(Math.toRadians(30))* RADIUS,
                width/2 - (float) Math.sin(Math.toRadians(30))* RADIUS,
                width/2 + (float)  Math.cos(Math.toRadians(30))*(RADIUS - INDENT),
                width/2 - (float) Math.sin(Math.toRadians(30))*(RADIUS - INDENT), paintLine);
        canvas.drawLine(width/2 + (float)  Math.cos(Math.toRadians(60))* RADIUS,
                width/2 - (float) Math.sin(Math.toRadians(60))* RADIUS,
                width/2 + (float)  Math.cos(Math.toRadians(60))*(RADIUS - INDENT),
                width/2 - (float) Math.sin(Math.toRadians(60))*(RADIUS - INDENT), paintLine);
        paintLine.setAlpha(50);
        canvas.drawLine(width/2 + (float)  Math.cos(Math.toRadians(30))* RADIUS,
                width/2 + (float) Math.sin(Math.toRadians(30))* RADIUS,
                width/2 + (float)  Math.cos(Math.toRadians(30))*(RADIUS - INDENT),
                width/2 + (float) Math.sin(Math.toRadians(30))*(RADIUS - INDENT), paintLine);
        canvas.drawLine(width/2 - (float)  Math.cos(Math.toRadians(30))* RADIUS,
                width/2 + (float) Math.sin(Math.toRadians(30))* RADIUS,
                width/2 - (float)  Math.cos(Math.toRadians(30))*(RADIUS - INDENT),
                width/2 + (float) Math.sin(Math.toRadians(30))*(RADIUS - INDENT), paintLine);

    }


    private void drawSpeedNumbers(Canvas canvas){
        Paint paintText = new Paint();
        paintText.setStyle(Paint.Style.FILL);
        paintText.setColor(Color.BLACK);
        paintText.setTextSize(25);
        float r = 15f;
        canvas.drawText(txt0,  width/2 - (float)  Math.cos(Math.toRadians(30))* TEXT_RADIUS - r,
                width/2 + (float) Math.sin(Math.toRadians(30))* TEXT_RADIUS + r, paintText);

        canvas.drawText(txt20, width/2 - TEXT_RADIUS - r, width/2 + r, paintText);

        canvas.drawText(txt40, width/2 - (float)  Math.cos(Math.toRadians(30))* TEXT_RADIUS - r,
                width/2 - (float) Math.sin(Math.toRadians(30))* TEXT_RADIUS, paintText);

        canvas.drawText(txt60, width/2 - (float)  Math.cos(Math.toRadians(60))* TEXT_RADIUS - r,
                width/2 - (float) Math.sin(Math.toRadians(60))* TEXT_RADIUS, paintText);

        canvas.drawText(txt80, width/2 - r, width/2 - TEXT_RADIUS, paintText);

        canvas.drawText(txt100, width/2 + (float)  Math.cos(Math.toRadians(60))* TEXT_RADIUS - r,
                width/2 - (float) Math.sin(Math.toRadians(60))* TEXT_RADIUS, paintText);

        canvas.drawText(txt120, width/2 + (float)  Math.cos(Math.toRadians(30))* TEXT_RADIUS - r,
                width/2 - (float) Math.sin(Math.toRadians(30))* TEXT_RADIUS + r, paintText);

        canvas.drawText(txt140, width/2 + TEXT_RADIUS - r, width/2, paintText);

        canvas.drawText(txt160, width/2 + (float)  Math.cos(Math.toRadians(30))* TEXT_RADIUS - r,
                width/2 + (float) Math.sin(Math.toRadians(30))* TEXT_RADIUS + r, paintText);
    }


    private void setSpeed(int speed, Canvas canvas){
        final Paint paintArrow = new Paint();
        paintArrow.setColor(arrowColor);
        paintArrow.setStrokeWidth(5);
        assert(speed <= MAX_SPEED && speed >= MIN_SPEED);
        double a = 1.5 * (speed - 20);
        canvas.drawLine(width/2 - (float)  Math.cos(Math.toRadians(a))* ARROW_RADIUS,
                width/2 - (float) Math.sin(Math.toRadians(a))* ARROW_RADIUS,
                width/2, width/2, paintArrow);
        Paint speedPaint = new Paint();
        speedPaint.setStyle(Paint.Style.FILL);
        speedPaint.setColor(Color.BLACK);
        speedPaint.setTextSize(100);
        canvas.drawText(String.valueOf(speed), width/2 - 70, width/2 + 300, speedPaint);
    }

    private void setArrowColor(Paint paint, int color){
        paint.setColor(color);
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int displayWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        int displayHeight = getContext().getResources().getDisplayMetrics().heightPixels;

        width = Math.min(displayWidth, displayHeight);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }



}
