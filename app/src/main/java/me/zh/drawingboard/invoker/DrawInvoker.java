package me.zh.drawingboard.invoker;

import android.graphics.Canvas;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import me.zh.drawingboard.DrawingBoardInterface;
import me.zh.drawingboard.command.IDrawCommand;

/**
 * <b>Package:</b> me.zh.drawingboard <br>
 * <b>Create Date:</b> 2019-08-12  14:53 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b> 命令保存和撤销执行者 <br>
 */
public class DrawInvoker implements DrawingBoardInterface {
    /**
     * 会被绘制的路径命令
     */
    private List<IDrawCommand> mDrawingCommandList;
    /**
     * 取消的命令列表
     */
    private List<IDrawCommand> mRedoCommandList;

    public DrawInvoker() {
        mDrawingCommandList = new CopyOnWriteArrayList<>();
        mRedoCommandList = new CopyOnWriteArrayList<>();
    }

    /**
     * 增加一个命令
     */
    @Override
    public void addCommand(IDrawCommand command) {
        if (!mDrawingCommandList.contains(command)) {
            mDrawingCommandList.add(command);
        }
    }

    /**
     * 撤销上一个命令
     */
    @Override
    public void undo() {
        if (mDrawingCommandList.size() > 0) {
            //拿出最后一个命令进行撤销
            IDrawCommand command = mDrawingCommandList.get(mDrawingCommandList.size() - 1);
            mDrawingCommandList.remove(command);
            command.undo();
            mRedoCommandList.add(command);
        }
    }

    /**
     * 取消撤销
     */
    @Override
    public void redo() {
        if (mRedoCommandList.size() > 0) {
            IDrawCommand command = mRedoCommandList.get(mRedoCommandList.size() - 1);
            mRedoCommandList.remove(command);
            mDrawingCommandList.add(command);
        }
    }

    /**
     * 执行命令
     */
    public void execute(Canvas canvas) {
        for (IDrawCommand command : mDrawingCommandList) {
            command.draw(canvas);
        }
    }

    /**
     * 是否可以取消撤销
     */
    @Override
    public boolean isCanRedo() {
        return mRedoCommandList.size() > 0;
    }

    /**
     * 是否可以撤销
     */
    @Override
    public boolean isCanUndo() {
        return mDrawingCommandList.size() > 0;
    }

    @Override
    public void clean() {
        mRedoCommandList.addAll(mDrawingCommandList);
        mDrawingCommandList.clear();
    }
}