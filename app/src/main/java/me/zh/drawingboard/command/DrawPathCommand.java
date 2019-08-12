package me.zh.drawingboard.command;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * <b>Package:</b> me.zh.drawingboard.command <br>
 * <b>Create Date:</b> 2019-08-12  14:48 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b> 绘制路径命令 <br>
 */
public class DrawPathCommand implements IDrawCommand {
    /**
     * 本次命令的绘制路径
     */
    private Path mPath;
    /**
     * 绘制时使用的画笔
     */
    private Paint mPaint;

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public void undo() {
    }

    public void setPath(Path path) {
        mPath = path;
    }

    public void setPaint(Paint paint) {
        mPaint = paint;
    }

    public Path getPath() {
        return mPath;
    }

    public Paint getPaint() {
        return mPaint;
    }
}