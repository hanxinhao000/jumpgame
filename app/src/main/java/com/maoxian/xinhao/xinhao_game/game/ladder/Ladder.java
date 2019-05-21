package com.maoxian.xinhao.xinhao_game.game.ladder;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * 梯子 制造类,总计预载50个阶梯坐标，用链式结构
 */
public class Ladder {


    //梯子数据

    private LadderData mLadderData;

    /**
     * 梯子间隔
     */

    private int interval = -400;

    /**
     * 梯子初始化位置
     */

    private int initInterval = 0;

    /**
     * 缓存
     */

    public static ArrayList<LadderTempData> temp;


    //单例模式

    private static Ladder mLadder;

    //总共大小

    private int mSize = 0;

    private Paint mPaint;


    public static Ladder getInstance() {

        if (temp == null) {
            temp = new ArrayList<>();
        }

        if (mLadder == null) {


            synchronized (Ladder.class) {

                if (mLadder == null) {
                    mLadder = new Ladder();

                    return mLadder;

                } else {
                    return mLadder;
                }

            }

        } else {

            return mLadder;
        }

    }


    public int getSize() {

        return mSize;
    }

    //初始化梯子数据
    public void initData(Context mContext) {

        initInterval = mContext.getResources().getDisplayMetrics().heightPixels;

        Random random = new Random();

        for (int i = 0; i < 50; i++) {

            LadderData ladderData = new LadderData();

            ladderData.setLadderY((i * interval) + initInterval);
            ladderData.setLadderX(random.nextInt(mContext.getResources().getDisplayMetrics().widthPixels - 500) + 108);

            saveInitData(ladderData);
        }

        mPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(30);
        mPaint.setTextSize(30);
        mPaint.setColor(Color.parseColor("#ad0015"));

    }

    public Paint getLadderPaint() {

        return mPaint;
    }

    //存入梯子数据
    public void saveInitData(LadderData ladderData) {


        if (mLadderData == null) {

            mLadderData = ladderData;
            //mLadderData.setIndex(mLadderData.getIndex() == 0 ? 0 : -1);

        } else {

            LadderData ladderData1 = new LadderData();

            ladderData1.setLadderX(ladderData.getLadderX());

            ladderData1.setLadderY(ladderData.getLadderY());

            ladderData1.setmLadderData(mLadderData);


            mLadderData = ladderData1;


        }

        initIndex();
    }

    //初始化链式表index

    private void initIndex() {


        if (mLadderData == null) {
            mSize = 0;
            return;
        }

        LadderData ladderData = mLadderData;

        int size = 0;

        while (true) {


            if (ladderData == null) {
                mSize = size;
                break;

            } else {
                ladderData.setIndex(size);

                ladderData = ladderData.getmLadderData();

                size++;
            }


        }


    }


    //取出梯子数据

    public LadderData getLadderData() {


        if (mLadderData == null) {

            return null;
        } else {


            LadderData temp = mLadderData;


            for (int i = 0; i < mSize - 1; i++) {
                temp = temp.getmLadderData();


            }
            deleteLast();

            return temp;

        }

    }

    //删除最后一个

    private void deleteLast() {

        if (mLadderData == null) {

            return;
        } else {


            LadderData temp = mLadderData;
            LadderData temp2 = null;
            if (mLadderData.getmLadderData() == null) {
                mLadderData = null;
            }


            while (true) {

                if (temp == null) {

                    if (temp2 != null) {
                        temp2.setmLadderData(null);
                    } else {
                        mLadderData = null;
                    }

                    break;
                }

                if (temp != null && temp.getmLadderData() != null)
                    temp2 = temp;
                temp = temp.getmLadderData();


            }

            // showData();

            initIndex();


        }


    }

    //显示
    public void showData() {

        ArrayList<LadderData> arrayList = new ArrayList<>();

        LadderData mLadderData = this.mLadderData;
        while (true) {


            if (mLadderData != null) {

                arrayList.add(mLadderData);

                mLadderData = mLadderData.getmLadderData();

            } else {
                break;
            }


        }


        Log.e("总共数据", "showData: " + arrayList);

    }

    private ArrayList<LadderTempData> arrayList;

    public void setArrayList(ArrayList<LadderTempData> arrayList) {

        this.arrayList = arrayList;


    }
}
