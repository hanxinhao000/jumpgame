package com.maoxian.xinhao.xinhao_game.game.ladder;

public class LadderTempData {

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
    public String toString() {
        return "LadderTempData{" +
                "ladderX=" + ladderX +
                ", ladderY=" + ladderY +
                '}';
    }
}
