package com.maoxian.xinhao.xinhao_game.utils;

import android.content.Context;
import android.os.Handler;

import com.maoxian.xinhao.xinhao_game.application.XHApplication;

public class UIutils {


    public static Context getApplication() {

        return XHApplication.mContext;
    }


    public static Handler getHandler() {

        return XHApplication.mHandler;
    }

}
