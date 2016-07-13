package com.github.woff.SeaFight;

import java.util.ArrayList;
import java.util.Random;

public class FletShips {

    private ArrayList<Ship> listShips = new ArrayList<Ship>();
    private Map map;

    FletShips(Map map) {
        this.map = map;
        int[] decks = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
        for (int i = 0; i < 10; i++) {
            listShips.add(new Ship(decks[i]));
        }
    }

    //устанавливаем флот на карте
    public void setShips() {
        Random random = new Random();
        boolean result;
        int x, y;
        for (int i = 0; i < listShips.size(); i++) {
            result = false;
            do {
                x = random.nextInt(9) + 1;
                y = random.nextInt(9) + 1;
                if (veriCoordShip(x, y, i)) {
                    for (int j = 0; j < listShips.get(i).getNumberOfDecks(); j++) {
                        map.setMassivMap(ElementMap.SHIP_DECK, listShips.get(i).getShipCoordX(j), listShips.get(i).getShipCoordY(j));
                        result = true;
                    }
                } else result = false;
            } while (!result);
        }
    }

    public ArrayList<Ship> getShips() {
        return listShips;
    }

    //проверяем координаты палуб корабля и строим его
    private boolean veriCoordShip(int x, int y, int listShipIndex) {
        Random random = new Random();
        int vector = random.nextInt(4);
        boolean result = false;
        //цикл по количеству палуб корабля
        for (int j = 0; j < listShips.get(listShipIndex).getNumberOfDecks(); j++) {
            if (vector == 0) {
                if (x + listShips.get(listShipIndex).getNumberOfDecks() < 11 && map.verifCoord(x + j, y)) {
                    listShips.get(listShipIndex).setShipCoord(j, x + j, y);
                    result = true;
                } else {
                    result = false;
                    break;
                }
            }
            if (vector == 1) {
                if (x - listShips.get(listShipIndex).getNumberOfDecks() > 0 && map.verifCoord(x - j, y)) {
                    listShips.get(listShipIndex).setShipCoord(j, x - j, y);
                    result = true;
                } else {
                    result = false;
                    break;
                }
            }
            if (vector == 2) {
                if (y + listShips.get(listShipIndex).getNumberOfDecks() < 11 && map.verifCoord(x, y + j)) {
                    listShips.get(listShipIndex).setShipCoord(j, x, y + j);
                    result = true;
                } else {
                    result = false;
                    break;
                }
            }
            if (vector == 3) {
                if (y - listShips.get(listShipIndex).getNumberOfDecks() > 0 && map.verifCoord(x, y - j)) {
                    listShips.get(listShipIndex).setShipCoord(j, x, y - j);
                    result = true;
                } else {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
}
