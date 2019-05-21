package com.maoxian.xinhao.xinhao_game.game.collision;


import android.content.Context;
import android.util.Log;

import com.maoxian.xinhao.xinhao_game.game.gravity.GravityThread;
import com.maoxian.xinhao.xinhao_game.game.ladder.Ladder;
import com.maoxian.xinhao.xinhao_game.game.ladder.LadderTempData;

import java.util.ArrayList;

/**
 * 控制小人和梯子的碰撞类,老规矩，单利
 */
public class ControlTheCollision {


    private static ControlTheCollision mControlTheCollision;


    private ArrayList<LadderTempData> arrayList;

    //过滤一下坐标
    private static ArrayList<LadderTempData> garr;


    public static ControlTheCollision getControlTheCollision() {

        if (garr == null) {
            garr = new ArrayList<>();
        }


        if (mControlTheCollision == null) {


            synchronized (ControlTheCollision.class) {


                if (mControlTheCollision == null) {

                    mControlTheCollision = new ControlTheCollision();

                    return mControlTheCollision;

                } else {


                    return mControlTheCollision;
                }

            }


        } else {


            return mControlTheCollision;
        }


    }

    /**
     * 初始化
     */

    public void collision(ArrayList<LadderTempData> arrayList, int mLocationY, int x, GravityThread gravityThread, Context mContext) {

        garr.clear();

        for (int i = 0; i < arrayList.size(); i++) {
            LadderTempData ladderTempData = arrayList.get(i);
            int ladderY = ladderTempData.getLadderY();

            if (ladderY > 0) {
                garr.add(ladderTempData);

            }

            if (arrayList.size() < 5) {

                Ladder.temp.addAll(arrayList);
                arrayList.clear();


                Ladder.getInstance().initData(mContext);
                return;
            }

            if (arrayList.get(i).getLadderY() > mContext.getResources().getDisplayMetrics().heightPixels) {

                LadderTempData remove = arrayList.remove(i);
                i--;

            }

        }


        for (int i = 0; i < garr.size(); i++) {


            LadderTempData ladderTempData = garr.get(i);

            int ladderY = ladderTempData.getLadderY();
            int ladderX = ladderTempData.getLadderX();

            Log.e("坐标 X", "x:" + x + " ladderX:" + ladderX + " stopladderX:" + (ladderX + 300));
            Log.e("坐标 Y", "t:" + (ladderY + 5) + " b:" + (ladderY - 5) + " mLocationY:" + mLocationY);

            if (mLocationY < (ladderY + 50) && mLocationY > (ladderY - 50) && x > (ladderX - 40) && x < (ladderX + 300)) {

                gravityThread.height = ladderY + 20;

                Log.e("坐标", "--------------------------");
                break;

            } else {

                gravityThread.initX();
            }


            //ladderY 底部坐标
            //ladderY -40 顶部坐标
            //ladderX 左部坐标
            // ladderX + 300右部坐标


           /* int t = ladderY - 50;
            int b = ladderY;


            int l = ladderX;
            int r = ladderX + 300;

            int pY = mLocationY;
            int pX = x;

            Log.e("坐标", "top: " + (ladderY - 50) + " b:" + b + " mLocationY:" + mLocationY);

            if (pY < b && pY > t && pX > l && pY < r) {
                gravityThread.height = ladderY + 20;

                break;
            } else {
                gravityThread.initX();
            }
*/
        }


    }


}
