package com.github.woff.SeaFight;

import java.util.Random;

public class FireComp extends Fire {

    private int vectorFire;
    private Random random;

    FireComp(Map map, FletShips fletShips) {
        super(map, fletShips);
    }

    private int getVectorFire() {
        return vectorFire;
    }

    private void setVectorFire() {
        random = new Random();
        vectorFire = random.nextInt(4);
    }

    public void attemptKillShip() {
        do {
            if (nextCoordFire()) {
                switch (vectorFire) {
                    case 0:
                        fireCoordY--;
                        break;
                    case 1:
                        fireCoordX++;
                        break;
                    case 2:
                        fireCoordY++;
                        break;
                    case 3:
                        fireCoordX--;
                        break;
                }
            }

        } while (!true);
    }

    private boolean nextCoordFire() {

        boolean trueCoordFireNext = false;

        if (vectorFire == 0) {
            if (fireCoordY - 1 > 0) trueCoordFireNext = true;
            else trueCoordFireNext = false;
        }
        if (vectorFire == 1) {
            if (fireCoordX + 1 < 11) trueCoordFireNext = true;
            else trueCoordFireNext = false;
        }
        if (vectorFire == 2) {
            if (fireCoordY + 1 < 11) trueCoordFireNext = true;
            else trueCoordFireNext = false;
        }
        if (vectorFire == 3) {
            if (fireCoordX - 1 > 0) trueCoordFireNext = true;
            else trueCoordFireNext = false;
        }
        return trueCoordFireNext;
    }
}
