package me.zh.drawingboard;

import me.zh.drawingboard.command.IDrawCommand;

/**
 * <b>Package:</b> me.zh.drawingboard <br>
 * <b>Create Date:</b> 2019-08-12  15:05 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b> 画板API <br>
 */
public interface DrawingBoardInterface {
    /**
     * 增加一个命令
     *
     * @param command 命令
     */
    void addCommand(IDrawCommand command);

    /**
     * 撤销上一个命令
     */
    void undo();

    /**
     * 取消撤销
     */
    void redo();

    /**
     * 是否可以取消撤销
     *
     * @return true为可以取消撤销，false为不可以
     */
    boolean isCanRedo();

    /**
     * 是否可以撤销
     *
     * @return true为可以撤销，false为不可以
     */
    boolean isCanUndo();

    /**
     * 清空
     */
    void clean();
}