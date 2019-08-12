package me.zh.drawingboard.brush.impl;

import android.graphics.Path;

import me.zh.drawingboard.brush.IBrush;

/**
 * <b>Package:</b> me.zh.drawingboard.brush.impl <br>
 * <b>Create Date:</b> 2019-08-12  14:43 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b> 圆形笔触实现，命令模式中的Receiver <br>
 */
public class CircleBrush implements IBrush {

    @Override
    public void down(Path path, float x, float y) {
    }

    @Override
    public void move(Path path, float x, float y) {
        path.addCircle(x, y, 10, Path.Direction.CW);
    }

    @Override
    public void up(Path path, float x, float y) {
    }
}