package com.maoxian.xinhao.xinhao_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.maoxian.xinhao.xinhao_game.game.GameMainView;
import com.maoxian.xinhao.xinhao_game.game.accelerometer.Accelerometer;
import com.maoxian.xinhao.xinhao_game.game.gravity.Gravity;
import com.maoxian.xinhao.xinhao_game.game.gravity.GravityListener;
import com.maoxian.xinhao.xinhao_game.game.gravity.GravityThread;

public class MainActivity extends AppCompatActivity {


    private GameMainView gameMainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameMainView = findViewById(R.id.game_main_view);

        gameMainView.startGame();

        gameMainView.puaseGame();

        gameMainView.stopGame();




    }


}
