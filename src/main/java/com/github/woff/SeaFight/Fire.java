package com.github.woff.SeaFight;

public class Fire {
    private Map map;
    private FletShips fletShips;
    protected int fireCoordX;
    protected int fireCoordY;

    Fire(Map map, FletShips fletShips) {

        this.map = map;
        this.fletShips = fletShips;
    }

    public void setFireCoord(int x, int y) {
        fireCoordX = x;
        fireCoordY = y;
    }

    //Метод обработки выстрела возвращает:
    public FireResult fire() {
        FireResult resultFire = FireResult.MISS;
        int numDeckDestr = 0;
        int numShipDestr = 0;
        for (int i = 0; i < fletShips.getShips().size(); i++) {
            for (int j = 0; j < fletShips.getShips().get(i).getNumberOfDecks(); j++) {
                //проверяем подбили ли палубу
                if (fletShips.getShips().get(i).getShipCoordX(j) == fireCoordX && fletShips.getShips().get(i).getShipCoordY(j) == fireCoordY) {
                    if (map.getMassivMap(fireCoordX, fireCoordY) == ElementMap.SHIP_DECK) {
                        map.setMassivMap(ElementMap.DESTROYED_SHIP_DECKS, fireCoordX, fireCoordY);
                        fletShips.getShips().get(i).setShipDestroyedDeck(j, true);
                        resultFire = FireResult.HIT;
                        //проверяем убит ли корабль
                        for (int k = 0; k < fletShips.getShips().get(i).getNumberOfDecks(); k++) {
                            if (fletShips.getShips().get(i).getShipDestroyedDeck(k)) {
                                numDeckDestr++;
                            }
                        }
                        if (fletShips.getShips().get(i).getNumberOfDecks() == numDeckDestr) {
                            resultFire = FireResult.KILL;
                            fletShips.getShips().get(i).setShipDestroyed(true);

                            //проверяем убиты ли все корабли
                            for (int m = 0; m < fletShips.getShips().size(); m++) {
                                if (fletShips.getShips().get(m).getShipDestroyed()) numShipDestr++;
                            }
                            if (fletShips.getShips().size() == numShipDestr) resultFire = FireResult.WIN;


                        }
                    } else if (map.getMassivMap(fireCoordX, fireCoordY) == ElementMap.DESTROYED_SHIP_DECKS) {
                        resultFire = FireResult.REPEAT_FIRE;
                    }
                }
            }
        }
        if (resultFire == FireResult.MISS) map.setMassivMap(ElementMap.BOSS_SHOT, fireCoordX, fireCoordY);
        return resultFire;
    }

    // проверка попал/не попал
    public boolean hit(FireResult fireResultElement) {
        if (fireResultElement.compareTo(FireResult.HIT) == 0) return true;
        if (fireResultElement.compareTo(FireResult.KILL) == 0) return true;
        if (fireResultElement.compareTo(FireResult.WIN) == 0) return true;
        return false;
    }

    public boolean win(FireResult fireResultElement) {
        if (fireResultElement.compareTo(FireResult.WIN) == 0) return true;
        return false;
    }
}
