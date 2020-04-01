package NavalWars;

import java.util.*;
import java.lang.*;

public class Board {
    Scanner scr = new Scanner(System.in);
    Random autoGen = new Random();
    private String[] ship = {"Carrier", "Battleship", "Destroyer", "Cruiser", "Patrol"};
    private int[] shipSize = {5, 4, 3, 3, 2};
    private char[] shipsLetter = {'A', 'B', 'D', 'C', 'P'};
    private final int BOARDSIZE = 10;
    private char[][] upper, lower;
    int autoCol;
    int autoRow;
    boolean orientation;

    public Board() {
        upper = new char[BOARDSIZE][BOARDSIZE];
        for (int row = 0; row < BOARDSIZE; row++) {
            for (int col = 0; col < BOARDSIZE; col++) {
                upper[row][col] = '.';
            }
        }
        lower = new char[BOARDSIZE][BOARDSIZE];
        for (int row = 0; row < BOARDSIZE; row++) {
            for (int col = 0; col < BOARDSIZE; col++) {
                lower[row][col] = '.';
            }
        }
    }

    public void forceLegalShipPlacement(int i) {

        orientation = autoGen.nextBoolean();
        boolean wrongColumnNumber = true;
        autoCol = autoGen.nextInt(10);
        autoRow = autoGen.nextInt(10);

        //If horizontal:
        if (orientation) {
            while (wrongColumnNumber) {
                if ((10 - autoCol) < shipSize[i]) {
                    while ((10 - autoCol) < shipSize[i]) {
                        autoCol = autoGen.nextInt(10);
                    }
                }
                for (int k = 0; k < shipSize[i]; k++) {
                    if (lower[autoRow][autoCol + k] != '.') {
                        autoCol = autoGen.nextInt(10);
                        autoRow = autoGen.nextInt(10);
                        wrongColumnNumber = true;
                        break;
                    }
                    wrongColumnNumber = false;
                }
            }

        } else {
            while (wrongColumnNumber) {
                if ((10 - autoRow) < shipSize[i]) {
                    while ((10 - autoRow) < shipSize[i]) {
                        autoRow = autoGen.nextInt(10);
                    }
                }
                for (int k = 0; k < shipSize[i]; k++) {
                    if (lower[autoRow + k][autoCol] != '.') {
                        autoRow = autoGen.nextInt(10);
                        autoCol = autoGen.nextInt(10);
                        wrongColumnNumber = true;
                        break;
                    }
                    wrongColumnNumber = false;
                }
            }
        }
    }


    public void autoPlaceShips() {

        for (int i = 0; i < ship.length; i++) {
            forceLegalShipPlacement(i);
            if (orientation) {
                for (int j = 0; j < shipSize[i]; j++) {
                    lower[autoRow][autoCol + j] = shipsLetter[i];
                }
            } else {
                for (int j = 0; j < shipSize[i]; j++) {
                    lower[autoRow + j][autoCol] = shipsLetter[i];
                }
            }
        }
    }

    public void manualPlaceShips() {

        boolean needsNewSpot = false;

        System.out.println("Automatically populate your ships, or Manually place them? Press A for auto, M for manual.");
        char keyPress = scr.next().charAt(0);

        while (keyPress != 'M' && keyPress != 'm' && keyPress != 'A' && keyPress != 'a') {
            System.out.println("Please enter either an \"A\" or and \"M\"");
            keyPress = scr.next().charAt(0);
        }
        if (keyPress == 'M' || keyPress == 'm') {

            System.out.println("Things to keep in mind when placing your ships on the board: ");
            System.out.println("Battleships need 4 spaces");
            System.out.println("Aircraft Carriers need 5 spaces");
            System.out.println("Destroyers need 3 spaces");
            System.out.println("Cruisers need 3 spaces");
            System.out.println("Patrol Boats need 2 spaces");

            //Assign the location of each ship
            for (int i = 0; i < ship.length; i++) {
                boolean vertical = false;
                boolean horizontal = false;

                System.out.println("How would you like to place your " + ship[i] + "?");
                System.out.println("Please enter either \"H\" for horizontally, or \"V\" for vertically:");
                String orientation = scr.next();
                orientation = orientation.toUpperCase();

                boolean conditionNotMet = true;
                //Check to make sure an entry is either an H or a V
                if (orientation.equals("H") || orientation.equals("V")) {
                    conditionNotMet = false;
                } else {
                    while (conditionNotMet) {
                        System.out.println("Incorrect format. Please enter either \"H\" for horizontally, or \"V\" for vertically.");
                        orientation = scr.next();
                        orientation = orientation.toUpperCase();
                        if (orientation.equals("H") || orientation.equals("V")) {
                            conditionNotMet = false;
                        }
                    }
                }

                if (orientation.equals("H")) {
                    System.out.println("You chose horizontally.");
                    horizontal = true;
                    vertical = false;

                }
                if (orientation.equals("V")) {
                    System.out.println("You chose vertically.");
                    vertical = true;
                    horizontal = false;
                }

                System.out.println("Where would you like to place your " + ship[i] + "?");
                System.out.println("Please type the row letter to start: ");

                char setRow = scr.next().charAt(0);
                int rowNum = setRow - 'A';

                while (rowNum > 9 || rowNum < 0) {
                    System.out.println("Please enter a letter \"A through J\"");
                    setRow = scr.next().charAt(0);
                    setRow = Character.toUpperCase(setRow);
                    rowNum = setRow - 'A';
                }

                System.out.println("Please enter the column number of the first spot of your " + ship[i] + ":");
                int setCol = scr.nextInt();
                if (setCol > 9 || setCol < 0) {
                    while (setCol > 9 || setCol < 0) {
                        System.out.println("Please enter 0-9.");
                        setCol = scr.nextInt();
                    }
                }
                
//            Check to see if the current ship will fit the given coordinates - in relation to ships length
//             If not, tell the player to input new values and check the new values
//            If so, fill the ships coordinates on the lower map
                if (horizontal) {
                    for (int k = 0; k < shipSize[i]; k++) {
                        if (10 - setCol > shipSize[i]) {
                            if (lower[rowNum][setCol + k] != '.') {
                                System.out.println("Please double check your spaces! Try again.");
                                needsNewSpot = true;
                            }
                        }
                    }
                    if (10 - setCol > shipSize[i]) {
                        System.out.println("Your ship will fit!");
                        for (int j = 0; j < shipSize[i]; j++) {
                            lower[rowNum][setCol + j] = shipsLetter[i];

                        }
                    } else {
                        while (10 - setCol < shipSize[i] && !needsNewSpot) {
                            System.out.println("Your ship will NOT fit! Remember, a " + ship[i] + " needs at least " + shipSize[i] + " spaces!");
                            System.out.println("This means the farthest right you can place your " + ship[i] + " is column " + (10 - shipSize[i]) + "!");
                            System.out.println("Please enter a new number: ");
                            setCol = scr.nextInt();
                        }
                        System.out.println("Your ship will fit!");
                        for (int j = 0; j < shipSize[i]; j++) {
                            lower[rowNum][setCol + j] = shipsLetter[i];
                        }
                    }
                    System.out.println(lowerToString());
                }

                if (vertical) {
                    for (int k = 0; k < shipSize[i]; k++) {
                        if (10 - rowNum > shipSize[i]) {
                            if (lower[rowNum + k][setCol] != '.') {
                                System.out.println("Please double check your spaces! Try again.");
                                needsNewSpot = true;
                            }
                        }
                    }
                    if (10 - rowNum > shipSize[i]) {
                        System.out.println("Your ship will fit!");
                        for (int j = 0; j < shipSize[i]; j++) {
                            lower[rowNum + j][setCol] = shipsLetter[i];

                        }
                    } else {
                        while (10 - rowNum < shipSize[i] && ! needsNewSpot) {
                            System.out.println("Your ship will NOT fit! Remember, a " + ship[i] + " needs at least " + shipSize[i] + " spaces!");
                            System.out.println("This means the farthest down you can place your " + ship[i] + " is row " + (char) ('A' + shipSize[i]) + "!");
                            System.out.println("Please enter a new row Letter: ");
                            setRow = scr.next().charAt(0);
                            rowNum = setRow - 'A';
                        }
                        System.out.println("Your ship will fit!");
                        for (int j = 0; j < shipSize[i]; j++) {
                            lower[rowNum + j][setCol] = shipsLetter[i];
                        }
                    }
                    System.out.println(lowerToString());
                } else {
                    autoPlaceShips();
                    System.out.println("Automatically... Done.");
                }

            }
        }
    }

    public boolean shootAt(char row, int col) {
        int rowNum = row - 'A';
        if (lower[rowNum][col] == '.' || lower[rowNum][col] == '#' || lower[rowNum][col] == 'o') {
            System.out.println("Missed!");
            lower[rowNum][col] = 'o';
            return false;
        } else {
            char hitLetter = lower[rowNum][col];
            System.out.println(row + "" + col + (" is a HIT!"));
            for (int i = 0; i < shipsLetter.length; i++) {
                if (hitLetter == shipsLetter[i]) {
                    shipSize[i] = shipSize[i] - 1;
                    System.out.println("The " + ship[i] + " was hit!");
                }
                if (shipSize[i] == 0) {
                    System.out.println("You sunk my " + ship[i] + "!");
                }
            }
            lower[rowNum][col] = '#';
            return true;
        }

    }

    public void recordHit(char row, int col) {
        int rowNum = row - 'A';
        upper[rowNum][col] = '#';
    }

    public void recordMiss(char row, int col) {
        int rowNum = row - 'A';
        upper[rowNum][col] = 'o';
    }

    public String toString() {
        StringBuilder result = new StringBuilder("   0  1  2  3  4  5  6  7  8  9\n");
        for (int row = 0; row < BOARDSIZE; row++) {
            result.append((char) ('A' + row)).append("  ");
            for (int col = 0; col < BOARDSIZE; col++) {
                result.append(upper[row][col]).append("  ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public String lowerToString() {
        StringBuilder result = new StringBuilder("  0  1  2  3  4  5  6  7  8  9\n");
        for (int row = 0; row < BOARDSIZE; row++) {
            result.append((char) ('A' + row)).append(" ");
            for (int col = 0; col < BOARDSIZE; col++) {
                result.append(lower[row][col]).append("  ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public boolean hasNoShips() {
        return shipSize[0] == 0 && shipSize[1] == 0 && shipSize[2] == 0 && shipSize[3] == 0 && shipSize[4] == 0;

    }

    public static void main(String[] a) {
        Board b = new Board();
        System.out.println(b);
        System.out.println(b.lowerToString());

    }

}
