package com.maoxian.xinhao.xinhao_game.game.accelerometer;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.maoxian.xinhao.xinhao_game.game.GameMainView;

import java.util.Arrays;

import static android.view.OrientationEventListener.ORIENTATION_UNKNOWN;

/**
 * 重力感应获取类
 */
public class Accelerometer implements SensorEventListener, Runnable {

    private Sensor mSensor;

    private SensorManager sm;

    private Context mContext;

    private GameMainView gameMainView;

    /**
     * 重力加速度的G
     */

    private double g = 9.8;

    /**
     * 默认速度为10m/s
     */

    private int mV = 100;
    /**
     * 设置物体重量【增加快慢】
     */

    private long kg = 150;


    //0 = 表示不动 1 = 向左 2 = 向右
    private int loca = 0;


    private AccelerometerListener mAccelerometerListener;

    private static Accelerometer mAccelerometer;

    /**
     * 获取实例
     *
     * @return
     */


    public void setGameMainView(GameMainView gameMainView) {

        if (gameMainView == null) {

            throw new RuntimeException("gameMainView is null");
        }

        this.gameMainView = gameMainView;

    }

    public static Accelerometer getInstance() {

        if (mAccelerometer == null) {
            synchronized (Accelerometer.class) {

                if (mAccelerometer == null) {

                    mAccelerometer = new Accelerometer();
                    return mAccelerometer;

                } else {

                    return mAccelerometer;
                }

            }

        } else {

            return mAccelerometer;

        }


    }


    /**
     * 初始化
     */

    public void init(Context context) {

        this.mContext = context;
        sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);

        new Thread(this).start();

    }

    /**
     * 设置重力加速度监听
     *
     * @param accelerometerListener
     */

    public void setAccelerometerListener(AccelerometerListener accelerometerListener) {

        mAccelerometerListener = accelerometerListener;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;

       /* if (mAccelerometerListener != null) {

            mAccelerometerListener.x(values[0] * 10);
        }*/

        float x = values[0] * 10;

        x = -x;
        //向右
        if (x > 10) {

            loca = 1;

        }

        //向左
        if (x < -10) {

            loca = 2;

        }

        //表示不动
        if (x < 10 && x > -10) {
            loca = 0;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }

    @Override
    public void run() {


        /**
         *
         * 这块这样写可能不太好，有大神可以给我建议一哈（捂脸笑）
         *
         * 监视左右运动[这样比较平滑一点]
         *
         */

        while (true) {

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch (loca) {


                case 0:
                    //什么也不做
                    break;
                case 1:

                    float gameX = gameMainView.getGameX();
                    gameX += 5;

                    if (gameX > mContext.getResources().getDisplayMetrics().widthPixels) {
                        gameX = 0;
                    }


                    if (mAccelerometerListener != null) {
                        mAccelerometerListener.x(gameX, true);
                    }
                    break;
                case 2:
                    float game2X = gameMainView.getGameX();
                    game2X -= 5;

                    if (game2X < 0) {
                        game2X = mContext.getResources().getDisplayMetrics().widthPixels;
                    }

                    if (mAccelerometerListener != null) {
                        mAccelerometerListener.x(game2X, false);
                    }
                    break;


            }


        }


    }

    /**
     *
     *
     *
     */
    private double gravityM(long time) {


        return 0.5 * g * kg * ((time * 0.001) * (time * 0.001));
    }

}
