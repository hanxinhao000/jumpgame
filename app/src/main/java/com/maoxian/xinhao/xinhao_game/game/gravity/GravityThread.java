package com.maoxian.xinhao.xinhao_game.game.gravity;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.maoxian.xinhao.xinhao_game.game.ladder.LadderTempData;
import com.maoxian.xinhao.xinhao_game.utils.UIutils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class GravityThread extends Thread {

    /**
     * 重力加速度的G
     */

    private double g = 9.8;

    /**
     * 默认速度为10m/s
     */

    private int mV = 100;

    /**
     * 获取起始时间
     */

    private long startTime = System.currentTimeMillis();

    /**
     * 设置物体重量【增加快慢】
     */

    private long kg = 150;

    /**
     * 跳跃高度
     */

    private int jumpH = 1000;

    /**
     * 伪速度
     */

    private ArrayList<Double> arrayList = new ArrayList<>();

    /**
     * 获取上下文
     */
    private Context mContext;

    private GravityListener mGravityListener;

    ArrayList<LadderTempData> arrayListladder;

    private int mY;

    /**
     * 是否结束线程
     */

    private boolean isStop = false;

    /**
     * 不建议使用这个终止进程，浪费资源
     *
     * @param isStop
     */
    @Deprecated
    public void setStop(boolean isStop) {

        this.isStop = isStop;
    }

    /**
     * 默认高度
     */

    public int height = 0;

    /**
     * 线程是否停止？
     *
     * @return
     */
    public boolean isStop() {
        return isStop;
    }

    /**
     * 是倒转还是下落
     */

    private boolean isUp2Down = false;

    /**
     * 创建一条新的线程获得这些信息
     *
     * @param gravityListener
     * @param y
     */
    public GravityThread(GravityListener gravityListener, int y, Context mContext) {

        mGravityListener = gravityListener;

        mY = y;

        this.mContext = mContext;

        getX();

    }

    //获取屏幕底部

    private void getX() {

        height = (mContext.getResources().getDisplayMetrics().heightPixels - 200);
        // height -= 1500;  测试用


    }

    /**
     * 初始化底部碰撞到默认值
     */
    public void initX() {

        height = (mContext.getResources().getDisplayMetrics().heightPixels - 200);
    }

    //获取弹跳点

    public void setHeight(int height) {

        this.height = height;
    }

    @Override
    public void run() {

        while (!isStop) {


            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long stopTime = System.currentTimeMillis();


            long t = stopTime - startTime;

            // double v = gravityM(t);


            if (isUp2Down) {

                // Log.e("自由落体", "run: " + "超过了屏幕的底部");
                /*printTimeUP(t);
                double parabolic = getParabolic(t);
                mGravityListener.location(parabolic + height);*/


                Collections.reverse(arrayList);

                for (int i = 0; i < arrayList.size(); i++) {

                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mGravityListener.location(arrayList.get(i));

                }
                arrayList.clear();
                isUp2Down = false;
                startTime = System.currentTimeMillis();
            } else {

                printTime(t);


                double v = gravityM(t);

                v = v + jumpH;

                if (v > height) {
                    isUp2Down = true;


                    if(height == mContext.getResources().getDisplayMetrics().heightPixels - 200){

                        UIutils.getHandler().post(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(mContext, "你凉了", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        startJumpUp();
                    }
                }

                arrayList.add(v);

                if (mGravityListener != null) {

                    mGravityListener.location(v);
                }
            }


        }


    }

    //开始弹跳

    private void startJumpUp() {


        final int[] x = {0};

        new Thread(new Runnable() {
            @Override
            public void run() {


                while (true) {

                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    x[0]++;

                    if (arrayListladder != null && arrayListladder.size() > 0) {

                        for (int i = 0; i < arrayListladder.size(); i++) {


                            arrayListladder.get(i).setLadderY(arrayListladder.get(i).getLadderY() + x[0]);


                        }

                    }

                    if (x[0] > 40) {
                        break;
                    }


                }

            }
        }).start();

    }


    public void setArrayList(ArrayList<LadderTempData> arrayList) {

        arrayListladder = arrayList;
    }

    public ArrayList<LadderTempData> getArrayListladder() {

        return arrayListladder;
    }

    /**
     * 返回抛物位置
     *
     * @return
     */

    private double getParabolic(long time) {


        return mV * (time * 0.001) - (0.5 * g * ((time * 0.001) * (time * 0.001)));


    }

    /**
     * 返回重力坐标
     *
     * @param time
     * @return
     */

    private double gravityM(long time) {


        return 0.5 * g * kg * ((time * 0.001) * (time * 0.001));
    }

    /**
     * 输出Log
     *
     * @param time
     */

    private void printTime(long time) {

        Date date = new Date(time);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss");

        String format = simpleDateFormat.format(date);


        Log.e("自由落体", "自由落体:" + format + " 秒位移是" + gravityM(time) + " 米");
    }

    /**
     * 输出log向上
     */

    private void printTimeUP(long time) {

        Date date = new Date(time);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss");

        String format = simpleDateFormat.format(date);


        Log.e("自由落体", "向上抛物:" + format + " 秒位移是" + (getParabolic(time) + height) + " 米");
    }
}
