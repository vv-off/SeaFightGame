package com.github.woff.SeaFight;

public class Map {

    private ElementMap massivMap[][];
    private int sizeMap;

    Map(int size) {
        sizeMap = size;
        massivMap = new ElementMap[sizeMap][sizeMap];

        for (int i = 0; i < sizeMap; i++) {
            massivMap[i][0] = ElementMap.COAST;
            massivMap[0][i] = ElementMap.COAST;
        }

        for (int i = 1; i < sizeMap - 1; i++) {
            for (int j = 1; j < sizeMap - 1; j++) {
                massivMap[i][j] = ElementMap.WATER;
            }
        }
    }

    public ElementMap getMassivMap(int x, int y) {
        return massivMap[y][x];
    }

    public void setMassivMap(ElementMap value, int x, int y) {
        massivMap[y][x] = value;
    }

    public int getSizeMap() {
        return sizeMap;
    }

    //проверяем координату
    public boolean verifCoord(int x, int y) {
        final int[][] arroundCoord = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
        boolean result = false;

        if (getMassivMap(x, y) != ElementMap.SHIP_DECK) {
            for (int i = 0; i < 8; i++) {
                if (getMassivMap(x + arroundCoord[i][0], y + arroundCoord[i][1]) != ElementMap.SHIP_DECK) {
                    result = true;
                } else {
                    result = false;
                    break;
                }
            }
        } else result = false;
        return result;
    }

    public void drawMap(boolean visibleShip) {
        char[] ch = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
        System.out.print("    ");
        for (int i = 0; i < getSizeMap() - 2; i++) System.out.print(ch[i] + " ");
        System.out.println();
        for (int i = 1; i < getSizeMap() - 1; i++) {
            if (i < getSizeMap() - 2) System.out.print(i + "   ");
            else System.out.print(i + "  ");
            for (int j = 1; j < getSizeMap() - 1; j++) {
                if (visibleShip) System.out.print(drawElementMap(getMassivMap(i, j)) + " ");
                else {
                    if (getMassivMap(i, j).compareTo(ElementMap.SHIP_DECK) == 0)
                        System.out.print(drawElementMap(ElementMap.WATER) + " ");
                    else System.out.print(drawElementMap(getMassivMap(i, j)) + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    private char drawElementMap(ElementMap elementMap) {
        char elementSymbol;
        switch (elementMap) {
            case COAST:
                elementSymbol = '0';
                break;
            case WATER:
                elementSymbol = '1';
                break;
            case SHIP_DECK:
                elementSymbol = '7';
                break;
            case DESTROYED_SHIP_DECKS:
                elementSymbol = '9';
                break;
            case BOSS_SHOT:
                elementSymbol = '3';
                break;
            default:
                elementSymbol = 'e';
        }
        return elementSymbol;
    }
}
