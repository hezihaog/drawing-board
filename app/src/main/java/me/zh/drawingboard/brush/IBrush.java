package me.zh.drawingboard.brush;

import android.graphics.Path;

/**
 * <b>Package:</b> me.zh.drawingboard.brush <br>
 * <b>Create Date:</b> 2019-08-12  14:36 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b> 笔触接口，只定义笔触的Api <br>
 */
public interface IBrush {
    /**
     * 触点接触时
     *
     * @param path 路径
     * @param x    按下的x坐标
     * @param y    按下的y坐标
     */
    void down(Path path, float x, float y);

    /**
     * 触点移动时
     *
     * @param path 路径
     * @param x    触点移动的x坐标
     * @param y    触点移动的y坐标
     */
    void move(Path path, float x, float y);

    /**
     * 触点离开时
     *
     * @param path 路径
     * @param x    触点离开时的x坐标
     * @param y    触点离开时的y坐标
     */
    void up(Path path, float x, float y);
}