package com.maoxian.xinhao.xinhao_game.game.gravity;


import android.content.Context;
import android.graphics.Paint;
import android.util.Log;

/**
 * 重力坐标计算类
 */

public class Gravity {

    /**
     * 重力加速度，自带画笔
     */

    private Paint mPaint;

    /**
     * 起始下落位置
     */

    private int mY = 0;


    private Context mContext;


    /**
     * 是否坐标正在输出
     */
    private boolean isRun = false;

    /**
     * 是否强制结束
     */

    private boolean isStop = false;

    /**
     * 传递context
     *
     * @param mContext
     */

    private GravityListener mGravityListener;
    private GravityThread gravityThread;


    public Gravity(Context mContext) {
        this.mContext = mContext;


    }

    /**
     * 传递y轴起点坐标
     * <p>
     * 重力加速度公式: g=G*M/(r^2)  g=9.80665 m/s^2
     *
     * @param y
     */

    public void setCoordinates(int y) {
        mY = y;
    }

    /**
     * 返回自带画笔，不调用不初始化
     *
     * @return
     */

    public Paint getPaint() {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setTextSize(60);
        }
        return mPaint;
    }

    /**
     * 设置监听
     *
     * @param mGravityListener
     */

    public void setGravityListener(GravityListener mGravityListener) {

        this.mGravityListener = mGravityListener;

    }

    /**
     * 获取内部线程
     *
     * @return
     */
    public GravityThread getGravityThread() {

        return gravityThread;
    }

    /**
     * 开始动画动作
     */

    public void start() {


        gravityThread = new GravityThread(mGravityListener, mY, mContext);

        gravityThread.start();


    }


}
