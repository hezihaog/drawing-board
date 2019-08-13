package me.zh.drawingboard;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import me.zh.drawingboard.brush.IBrush;
import me.zh.drawingboard.brush.impl.CircleBrush;
import me.zh.drawingboard.brush.impl.NormalBrush;
import me.zh.drawingboard.command.DrawPathCommand;
import me.zh.drawingboard.widget.DrawCanvas;

/**
 * @author wally
 */
public class MainActivity extends AppCompatActivity {
    private DrawCanvas vDrawCanvas;
    private Button vChangeToRed;
    private Button vChangeToGreen;
    private Button vChangeToBlue;
    private Button vSwitchNormalPaint;
    private Button vSwitchCirclePaint;
    private Button vUndo;
    private Button vRedo;
    private Button vClean;

    private Paint mPaint;
    private IBrush mPaintBrush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView(findViewById(android.R.id.content));
        bindView();
        initPaint();
    }

    private void findView(View view) {
        vDrawCanvas = view.findViewById(R.id.draw_canvas);
        vChangeToRed = view.findViewById(R.id.change_to_red);
        vChangeToGreen = view.findViewById(R.id.change_to_green);
        vChangeToBlue = view.findViewById(R.id.change_to_blue);
        vSwitchNormalPaint = view.findViewById(R.id.switch_normal_paint);
        vSwitchCirclePaint = view.findViewById(R.id.switch_circle_paint);
        vUndo = view.findViewById(R.id.undo);
        vRedo = view.findViewById(R.id.redo);
        vClean = view.findViewById(R.id.clean);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void bindView() {
        vChangeToRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePaintColor(Color.parseColor("#FF0000"));
            }
        });
        vChangeToGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePaintColor(Color.parseColor("#FF00FF00"));
            }
        });
        vChangeToBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePaintColor(Color.parseColor("#FF0000FF"));
            }
        });
        //切换笔触
        vSwitchNormalPaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePaintNormalBrush();
            }
        });
        vSwitchCirclePaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePaintCircleBrush();
            }
        });
        //撤销
        vUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vDrawCanvas.undo();
            }
        });
        //取消撤销
        vRedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vDrawCanvas.redo();
            }
        });
        vClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vDrawCanvas.clean();
            }
        });
        vDrawCanvas.setOnTouchListener(new View.OnTouchListener() {
            /**
             * 绘制路径命令
             */
            private DrawPathCommand mPathCommand;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    mPathCommand = new DrawPathCommand();
                    mPathCommand.setPaint(mPaint);
                    mPathCommand.setPath(new Path());
                    mPaintBrush.down(mPathCommand.getPath(), event.getX(), event.getY());
                } else if (action == MotionEvent.ACTION_MOVE) {
                    mPaintBrush.move(mPathCommand.getPath(), event.getX(), event.getY());
                } else if (action == MotionEvent.ACTION_UP) {
                    mPaintBrush.up(mPathCommand.getPath(), event.getX(), event.getY());
                    vDrawCanvas.addCommand(mPathCommand);
                }
                return true;
            }
        });
    }

    private void initPaint() {
        //初始化画笔
        changePaintColor(Color.parseColor("#FFFFFF"));
        //初始化笔触
        changePaintNormalBrush();
    }

    private void changePaintColor(int color) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
        mPaint.setStrokeWidth(8);
        if (mPaintBrush instanceof NormalBrush) {
            changePaintNormalBrush();
        } else if (mPaintBrush instanceof CircleBrush) {
            changePaintCircleBrush();
        }
    }

    /**
     * 改变画笔的笔触为直线笔触
     */
    private void changePaintNormalBrush() {
        mPaintBrush = new NormalBrush();
        mPaint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 改变画笔的笔触为圆形笔触
     */
    private void changePaintCircleBrush() {
        mPaintBrush = new CircleBrush();
        mPaint.setStyle(Paint.Style.FILL);
    }
}