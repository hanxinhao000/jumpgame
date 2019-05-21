package com.maoxian.xinhao.xinhao_game.game.ladder;


/**
 * 利用handler 轮训器思想做出的链式类
 */
public class LadderData {


    private int index = 0;


    private LadderData mLadderData;


    /**
     * 总共大小
     *
     */
    private int size = -1;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public LadderData getmLadderData() {
        return mLadderData;
    }

    public void setmLadderData(LadderData mLadderData) {
        this.mLadderData = mLadderData;
    }

    private int ladderX;

    private int ladderY;

    public int getLadderX() {
        return ladderX;
    }

    public void setLadderX(int ladderX) {
        this.ladderX = ladderX;
    }

    public int getLadderY() {
        return ladderY;
    }

    public void setLadderY(int ladderY) {
        this.ladderY = ladderY;
    }

    @Override
    public String
    toString() {
        return "LadderData{" +
                "index=" + index +
                '}';
    }
}
