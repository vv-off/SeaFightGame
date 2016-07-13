package com.github.woff.SeaFight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;


public class Main {


    public static void main(String args[]) throws IOException {
        CommandParsing commandParsing = new CommandParsing();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        commandParsing.setCommand(InputState.NONE.toString());
        String command;
        Random random = new Random();
        FireResult fireResultElement;

        Map yourMap = null;
        Map compMap = null;
        FletShips yourFlet = null;
        FletShips compFlet = null;
        Fire yourFire = null;
        FireComp compFire = null;

        out:
        while (true) {
            switch (commandParsing.getCommand()) {
                case NONE:
                    System.out.println("Введите start для начала игры.");
                    break;
                case START:
                    yourMap = new Map(12);
                    compMap = new Map(12);
                    yourFlet = new FletShips(yourMap);
                    compFlet = new FletShips(compMap);
                    yourFire = new Fire(compMap, compFlet);
                    compFire = new FireComp(yourMap, yourFlet);
                    System.out.println("Игра началась! Введите map чтобы расставить корабли.");
                    break;
                case MAP:
                    if (yourFlet != null) {
                        yourFlet.setShips();
                    }
                    if (compFlet != null) {
                        compFlet.setShips();
                    }
                    if (yourMap != null) {
                        yourMap.drawMap(true);
                    }
                    if (compMap != null) {
                        compMap.drawMap(false);
                    }
                    System.out.println("Корабли расставлены, введите координаты выстрела.");
                    break;
                case FIRE:
                    yourFire.setFireCoord(commandParsing.getXcoordFire(), commandParsing.getYcoordFire());
                    fireResultElement = yourFire.fire();
                    if (yourFire.hit(fireResultElement)) {
                        if (fireResultElement.compareTo(FireResult.HIT) == 0) System.out.println("Вы попали!");
                        if (fireResultElement.compareTo(FireResult.KILL) == 0)
                            System.out.println("Вы подбили корабль!");
                        if (fireResultElement.compareTo(FireResult.WIN) == 0) {
                            System.out.println("Вы выиграли");
                            break out;
                        }
                    } else {
                        if (fireResultElement.compareTo(FireResult.MISS) == 0)
                            System.out.println("Вы промахнулись. Ход соперника.");
                        if (fireResultElement.compareTo(FireResult.REPEAT_FIRE) == 0)
                            System.out.println("Промах, корабль уже подбит/убит ранее.");
                        compFire.setFireCoord(random.nextInt(10) + 1, random.nextInt(10) + 1);
                        do {
                            fireResultElement = compFire.fire();
                            if (compFire.hit(fireResultElement)) {
                                if (fireResultElement.compareTo(FireResult.HIT) == 0) {
                                    System.out.println("Соперник попал!");
                                    //пробуем добить корабль
                                    compFire.attemptKillShip();
                                }
                                if (fireResultElement.compareTo(FireResult.KILL) == 0) {
                                    System.out.println("Соперник подбил корабль!");
                                    compFire.setFireCoord(random.nextInt(10) + 1, random.nextInt(10) + 1);
                                }
                                if (fireResultElement.compareTo(FireResult.WIN) == 0) {
                                    System.out.println("Соперник выиграл");
                                    break out;
                                }
                            } else {
                                if (fireResultElement.compareTo(FireResult.MISS) == 0)
                                    System.out.println("Соперник промахнулся. Ваш ход.");
                                if (fireResultElement.compareTo(FireResult.REPEAT_FIRE) == 0)
                                    System.out.println("Промах, корабль уже подбит/убит ранее");
                                break;
                            }
                        } while (true);
                    }
                    yourMap.drawMap(true);
                    compMap.drawMap(false);
                    break;
                case ERROR_MAP_NOT_START:
                    System.out.println("Ошибка! Нельзя расставить корабли, игра не начата. Введите start.");
                    break;
                case ERROR_MAP_IS_FIGHTING:
                    System.out.println("Ошибка! Нельзя расставлять корабли! Идёт бой.");
                    System.out.println("Введите координаты встрела или start для создания новой игры.");
                    break;
                case ERROR_MAP_IS_ALREADY:
                    System.out.println("Ошибка! Корабли уже расставлены.");
                    System.out.println("Введите координаты выстрела или start для создания новой игры.");
                    break;
                case ERROR_FIRE_NOT_START:
                    System.out.println("Ошибка! Нельзя сделать выстрел, игра не начата.");
                    System.out.println("Введите start для начала игры.");
                    break;
                case ERROR_FIRE_NOT_MAP:
                    System.out.println("Ошибка! Нельзя сделать выстрел.");
                    System.out.println("Корабли не расставлены. Введите map чтобы расставить корабли.");
                    break;
                case HELP:
                    System.out.println("start  - начало новой игры");
                    System.out.println("map    - создать карту игры");
                    System.out.println("a1-j10 - координаты выстрела");
                    System.out.println("exit   - Выйти из игры");
                    break;
                case EXIT:
                    break out;
                case ERROR_COMMAND:
                    System.out.println("Ошибка! Неправильная команда, введите HELP для справки.");
                    break;
            }
            command = reader.readLine();
            if (command.compareTo("") != 0) commandParsing.setCommand(command);
        }
    }
}



