package com.github.woff.SeaFight;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParsing {

    private String command;
    private int xCoordFire;
    private int yCoordFire;
    private char[] ch = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
    private ArrayList<InputState> listState = new ArrayList<InputState>();

    public int getXcoordFire() {
        return xCoordFire;
    }

    public int getYcoordFire() {
        return yCoordFire;
    }

    public void setCommand(String str) {
        command = str;
    }

    private boolean coordinatesShot() {
        Pattern p = Pattern.compile("([a-j][1-9])|[a-j]([1][0])");
        Matcher m = p.matcher(command);

        if (m.matches()) {
            parseCoordinatesShot();
            return true;
        }
        return false;
    }

    private void parseCoordinatesShot() {
        String numStr = "";
        Pattern patChar = Pattern.compile("([a-j])");
        Matcher matcherChar = patChar.matcher(command);
        while (matcherChar.find()) {
            numStr = matcherChar.group();
        }
        for (int i = 0; i < ch.length; i++) {
            if (numStr.compareTo(Character.toString(ch[i])) == 0) {
                yCoordFire = i + 1;
            }
        }
        Pattern patNum = Pattern.compile("([0-9]+)");
        Matcher matcherNum = patNum.matcher(command);
        while (matcherNum.find()) {
            xCoordFire = Integer.parseInt(matcherNum.group());
        }
    }

    public InputState getCommand() {
        InputState state = InputState.ERROR_COMMAND;
        if (command.equalsIgnoreCase(InputState.NONE.toString())) {
            state = InputState.NONE;
        }
        if (command.equalsIgnoreCase(InputState.START.toString())) {
            listState.clear();
            listState.add(InputState.START);
            state = InputState.START;
        }
        if (command.equalsIgnoreCase(InputState.MAP.toString())) {
            if ((!listState.contains(InputState.START))) state = InputState.ERROR_MAP_NOT_START;
            else if (listState.contains(InputState.FIRE)) {
                state = InputState.ERROR_MAP_IS_FIGHTING;
            } else {
                if (listState.contains(InputState.MAP)) state = InputState.ERROR_MAP_IS_ALREADY;
                else {
                    listState.add(InputState.MAP);
                    state = InputState.MAP;
                }
            }
        }
        if (coordinatesShot()) {
            if ((!listState.contains(InputState.START))) state = InputState.ERROR_FIRE_NOT_START;
            else if (!listState.contains(InputState.MAP)) state = InputState.ERROR_FIRE_NOT_MAP;
            else {
                if (!listState.contains(InputState.FIRE)) listState.add(InputState.FIRE);
                state = InputState.FIRE;
            }
        }
        if (command.equalsIgnoreCase(InputState.HELP.toString())) {
            state = InputState.HELP;
        }
        if (command.equalsIgnoreCase(InputState.EXIT.toString())) {
            state = InputState.EXIT;
        }
        return state;
    }
}