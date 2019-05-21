package com.maoxian.xinhao.xinhao_game.game;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.maoxian.xinhao.xinhao_game.R;
import com.maoxian.xinhao.xinhao_game.game.accelerometer.Accelerometer;
import com.maoxian.xinhao.xinhao_game.game.accelerometer.AccelerometerListener;
import com.maoxian.xinhao.xinhao_game.game.collision.ControlTheCollision;
import com.maoxian.xinhao.xinhao_game.game.gravity.Gravity;
import com.maoxian.xinhao.xinhao_game.game.gravity.GravityListener;
import com.maoxian.xinhao.xinhao_game.game.ladder.Ladder;
import com.maoxian.xinhao.xinhao_game.game.ladder.LadderData;
import com.maoxian.xinhao.xinhao_game.game.ladder.LadderTempData;
import com.maoxian.xinhao.xinhao_game.utils.UIutils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 画图类
 */
public class GameMainView extends View implements GravityListener, AccelerometerListener {

    private Context mContext;
    private Gravity mGravity;
    private Paint mPaintGravity;

    private ArrayList<LadderTempData> arrayList;

    private float x = 0;

    private boolean isLR = false;

    //暂停游戏

    private boolean puase = false;

    //缓存
    private ArrayList<LadderTempData> temp;

    //底部背板
    private Bitmap b;


    private double mLocationY;
    private Accelerometer instance;
    private Ladder mLadderInstance;
    private Paint mLadderPaint;
    private Bitmap l;
    private Bitmap r;

    public GameMainView(Context context) {
        super(context);
        init(context);
    }

    public GameMainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameMainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    public void init(Context mContext) {
        this.mContext = mContext;
        mGravity = new Gravity(mContext);
        mGravity.setGravityListener(this);


        mPaintGravity = mGravity.getPaint();


        x = (mContext.getResources().getDisplayMetrics().widthPixels / 2);
        instance = Accelerometer.getInstance();
        instance.init(mContext);
        instance.setGameMainView(this);
        instance.setAccelerometerListener(this);

        mLadderInstance = Ladder.getInstance();

        mLadderInstance.initData(mContext);


        mLadderPaint = mLadderInstance.getLadderPaint();

        b = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.missions_progress_bar_on_b);

        l = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.likleft_r);

        r = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.likright_l);

        arrayList = new ArrayList<>();
        temp = new ArrayList<>();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        drawLadder(canvas);
        drawPeople(canvas);
        // canvas.drawLine(500, 500, 1000, 500, mLadderPaint);
    }


    /**
     * 开始游戏
     */

    public void startGame() {

        mGravity.start();
        // mGravity.getGravityThread().height = 500;
    }

    /**
     * 暂停游戏
     */

    public void puaseGame() {

        puase = !puase;

    }

    /**
     * 结束游戏
     */

    public void stopGame() {

    }

    /**
     * 画人
     *
     * @param canvas
     */
    private void drawPeople(Canvas canvas) {

        // canvas.drawText("x:" + x + " y:" + mLocationY, x + 40, (float) (mLocationY), mPaintGravity);
        canvas.drawText("■", x, (float) mLocationY, mPaintGravity);
        if (isLR) {
            //右
            canvas.drawBitmap(r, x - 50, (float) mLocationY - 140, mLadderPaint);

        } else {
            //左
            canvas.drawBitmap(l, x - 50, (float) mLocationY - 140, mLadderPaint);

        }


        ControlTheCollision.getControlTheCollision().collision(arrayList, (int) mLocationY, (int) x, mGravity.getGravityThread(), mContext);


    }

    /**
     * 画梯子
     *
     * @return
     */

    private void drawLadder(Canvas canvas) {


        /**
         *
         * 这块不建议这样写，谁产生的数据，谁管，这个类原本是画图的，只能管画图，记住这种思路就OK
         *
         */
        while (true) {

            LadderData ladderData = mLadderInstance.getLadderData();

            if (ladderData == null) {
                if (mGravity.getGravityThread().getArrayListladder() == null) {


                    Collections.reverse(arrayList);
                    Log.e("数据处理完成", "drawLadder: " + arrayList);

                    for (int i = 0; i < Ladder.temp.size(); i++) {

                        arrayList.add(0, Ladder.temp.get(i));

                    }
                    Ladder.temp.clear();
                    mGravity.getGravityThread().setArrayList(arrayList);
                    mLadderInstance.setArrayList(arrayList);
                }
                break;

            } else {


                int ladderX = ladderData.getLadderX();

                int ladderY = ladderData.getLadderY();

                LadderTempData ladderTempData = new LadderTempData();

                ladderTempData.setLadderX(ladderX);
                ladderTempData.setLadderY(ladderY);


                arrayList.add(ladderTempData);


            }
        }


        for (int i = 0; i < arrayList.size(); i++) {


            // canvas.drawText("x:" + arrayList.get(i).getLadderX() + " y:" + arrayList.get(i).getLadderY() + " stopX:" + (arrayList.get(i).getLadderX() + 300), arrayList.get(i).getLadderX(), arrayList.get(i).getLadderY() - 50, mLadderPaint);
            //canvas.drawLine(arrayList.get(i).getLadderX(), arrayList.get(i).getLadderY(), arrayList.get(i).getLadderX() + 108, arrayList.get(i).getLadderY(), mLadderPaint);
            canvas.drawBitmap(b,arrayList.get(i).getLadderX(),arrayList.get(i).getLadderY(),mLadderPaint);
        }


        Log.e("数据查看", "arrayList: " + arrayList.size());

    }

    public float getGameX() {

        return x;
    }

    /**
     * 子线程
     *
     * @param location
     */

    @Override
    public void location(double location) {
        mLocationY = location;

        UIutils.getHandler().post(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });

    }


    @Override
    public void x(float x, boolean isLR) {

        this.isLR = isLR;
        this.x = x;
        UIutils.getHandler().post(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });

    }
}
