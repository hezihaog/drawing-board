package me.zh.drawingboard.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import me.zh.drawingboard.DrawingBoardInterface;
import me.zh.drawingboard.command.IDrawCommand;
import me.zh.drawingboard.invoker.DrawInvoker;

/**
 * <b>Package:</b> me.zh.drawingboard.widget <br>
 * <b>Create Date:</b> 2019-08-12  14:35 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
public class DrawCanvas extends SurfaceView implements SurfaceHolder.Callback, DrawingBoardInterface {
    /**
     * 绘制执行者
     */
    private DrawInvoker mInvoker;
    /**
     * 绘制线程，是否正在运行
     */
    private boolean isRunning;
    /**
     * 是否可以绘制
     */
    private boolean isDrawing;
    /**
     * 绘制在Bitmap中
     */
    private Bitmap mPathBitmap;
    /**
     * 绘制线程
     */
    private DrawThread mDrawThread;

    public DrawCanvas(Context context) {
        super(context);
        init();
    }

    public DrawCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mInvoker = new DrawInvoker();
        mDrawThread = new DrawThread();
        getHolder().addCallback(this);
    }

    @Override
    public void addCommand(IDrawCommand command) {
        mInvoker.addCommand(command);
        isDrawing = true;
    }

    @Override
    public void undo() {
        isDrawing = true;
        mInvoker.undo();
    }

    @Override
    public void redo() {
        isDrawing = true;
        mInvoker.redo();
    }

    @Override
    public boolean isCanRedo() {
        return mInvoker.isCanRedo();
    }

    @Override
    public boolean isCanUndo() {
        return mInvoker.isCanUndo();
    }

    @Override
    public void clean() {
        isDrawing = true;
        mInvoker.clean();
    }

    /**
     * 绘制线程
     */
    private class DrawThread extends Thread {
        @Override
        public void run() {
            super.run();
            Canvas canvas = null;
            while (isRunning) {
                if (isDrawing) {
                    try {
                        canvas = getHolder().lockCanvas(null);
                        if (mPathBitmap == null) {
                            mPathBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
                        }
                        //绘制线
                        Canvas pathCanvas = new Canvas(mPathBitmap);
                        pathCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
                        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                        mInvoker.execute(pathCanvas);
                        canvas.drawBitmap(mPathBitmap, 0, 0, null);
                    } finally {
                        getHolder().unlockCanvasAndPost(canvas);
                    }
                    isDrawing = false;
                }
            }
        }
    }

    //--------------------------- 生命周期回调 ---------------------------

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRunning = true;
        mDrawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mPathBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        //销毁页面，将标志位设置为停止，停止线程
        isRunning = false;
        try {
            while (retry) {
                mDrawThread.join();
                retry = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}