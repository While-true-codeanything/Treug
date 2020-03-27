package com.example.treug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

/**
 * Created by Компьютер on 04.04.2017.
 */

public class DrawThread extends Thread {

    private SurfaceHolder surfaceHolder;

    private volatile boolean running = true;//флаг для остановки потока
    private Paint backgroundPaint = new Paint();


    {
        backgroundPaint.setColor(Color.BLUE);
        backgroundPaint.setStyle(Paint.Style.FILL);
    }

    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

    public void requestStop() {
        running = false;
    }

    public void drawRomb(Canvas canvas, float x, float y, float x1, float y1,int r){
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        double s=Math.sqrt(Math.pow((x1-x),2)+Math.pow((y1-y),2));
        canvas.drawLine(x,y,x1,y1,paint);
        canvas.drawLine(x1,y1,(float)(x1+s),y1,paint);
        canvas.drawLine((float)(x1+s),y1,x,y,paint);
        canvas.drawLine(x1,y1,(float)(x1+s/2),y1+(float)Math.sqrt((3/4.0)*s*s),paint);
        canvas.drawLine((float)(x1+s/2),y1+(float)Math.sqrt((3/4.0)*s*s),(float)(x1+s),y1,paint);
        canvas.drawLine((float)(x1+s/2),y1+(float)Math.sqrt((3/4.0)*s*s),(float)((x1+s/2)+s),y1+(float)Math.sqrt((3/4.0)*s*s),paint);
        canvas.drawLine((float)(x1+s/2),y1+(float)Math.sqrt((3/4.0)*s*s),(float)((x1+s/2)-s),y1+(float)Math.sqrt((3/4.0)*s*s),paint);
        canvas.drawLine((float)((x1+s/2)+s),y1+(float)Math.sqrt((3/4.0)*s*s),(float)(x1+s),y1,paint);
        canvas.drawLine((float)((x1+s/2)-s),y1+(float)Math.sqrt((3/4.0)*s*s),x1,y1,paint);
        r++;
        if(r < 7) {
            drawRomb( canvas, x1, y1, (float)(x1-s/2), y1+(float)Math.sqrt((3/4.0)*s*s), r);
            drawRomb( canvas, (float)(x1+s), y1, (float)(x1+s/2), y1+(float)Math.sqrt((3/4.0)*s*s), r);
        }
    }
    @Override
    public void run() {
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    drawRomb( canvas,  200, 200, 150, 300, 0);
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }

            }
        }
    }
}