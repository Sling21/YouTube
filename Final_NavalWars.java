package NavalWars;

import java.util.*;

import static java.lang.Thread.sleep;

public class NavalWars {
    private Board player, computer;
    Scanner scr = new Scanner(System.in);
    Random num = new Random();

    public void setup() {
        player = new Board();
        computer = new Board();
        player.manualPlaceShips();
        System.out.println(player.lowerToString());
        computer.autoPlaceShips();

    }

    public void playerTurn() {

        System.out.println("Enter the coordinates of your shot: ");
        char row = scr.next().charAt(0);
        int col = scr.nextInt();
        //Add stipulations that force player to enter valid coordinates
        if (computer.shootAt(row, col)) {
            player.recordHit(row, col);
        } else {

            player.recordMiss(row, col);
        }
        System.out.println(" ---- Top Board ----");
        System.out.println(player.toString());
        System.out.println(" --- Lower Board ----");
        System.out.println(player.lowerToString());
    }

    public void computerTurn() {
        //Maybe Add some AI eventually?

        int rowNum = num.nextInt(10);
        int col = num.nextInt(10);
        char row = (char) (rowNum + 'A');

        System.out.println("The computer shoots at: " + row + "" + col);
        if (player.shootAt(row, col)) {
            computer.recordHit(row, col);
        } else {
            computer.recordMiss(row, col);
        }
    }

    public void play() {
        while (! gameOver()) {
            playerTurn();
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            computerTurn();
        }
    }

    //Still need to test this fully

    public boolean gameOver() {
        if (player.hasNoShips() || computer.hasNoShips()) {
            if(player.hasNoShips()) {
                System.out.println("The smell of defeat hangs thick in the air!");
            } else {
                System.out.println("We are VICTORIOUS!!");
            }
            printFinalBoards();
            return true;
        } else {

            return false;
        }
    }

    public void printFinalBoards() {
        System.out.println();
        System.out.println("    ---PLAYER---");
        System.out.println(player.lowerToString());
        System.out.println("    ---Computer---");
        System.out.println(computer.lowerToString());
    }

    public static void main(String[] args) {
        NavalWars game = new NavalWars();
        game.setup();
        game.play();

    }

}

