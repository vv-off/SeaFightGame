package com.github.woff.SeaFight;

public class Deck {
    private int x;
    private int y;
    private boolean destroyed;

    Deck() {
        destroyed = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
}
