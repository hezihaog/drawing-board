package me.zh.drawingboard.command;

import android.graphics.Canvas;

/**
 * <b>Package:</b> me.zh.drawingboard.command <br>
 * <b>Create Date:</b> 2019-08-12  14:47 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b> 命令接口 <br>
 */
public interface IDrawCommand {
    /**
     * 绘制操作
     *
     * @param canvas 画布
     */
    void draw(Canvas canvas);

    /**
     * 撤销操作
     */
    void undo();
}