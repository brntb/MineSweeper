package minesweeper;

import java.util.Scanner;

public class GameController {

    private final Scanner scanner;
    private final MineSweeper mineSweeper;

    public GameController(Scanner scanner, MineSweeper mineSweeper) {
        this.scanner = scanner;
        this.mineSweeper = mineSweeper;
    }

    public void playGame() {
        initGame();

        while (!mineSweeper.isGameOver()) {
            String[] turnParameters = getUserTurn();
            int coordinateOne = Integer.parseInt(turnParameters[0]);
            int coordinateTwo = Integer.parseInt(turnParameters[1]);
            String choice = turnParameters[2];

            //if choice is free we just need to revel what cell is
            if ("free".equals(choice)) {

                //check if player stepped on mine
                if (mineSweeper.isCellMine(coordinateOne, coordinateTwo)) {
                    mineSweeper.setMinesVisible();
                    mineSweeper.printBoard();
                    System.out.println("You stepped on a mine and failed!");
                     break;
                }
                //explore cell if it is free
                if (mineSweeper.isCellOpen(coordinateOne, coordinateTwo)) {
                    mineSweeper.explore(coordinateOne, coordinateTwo);
                } else {
                    mineSweeper.showCell(coordinateOne, coordinateTwo);
                }

            } else{ //just need to mark cell as mine
                //if coordinates are already marked, unmark mine
                if (mineSweeper.isCellMarked(coordinateOne, coordinateTwo)) {
                    mineSweeper.unsetCell(coordinateOne, coordinateTwo);
                } else {
                    mineSweeper.setCell(State.MARKER, coordinateOne, coordinateTwo);
                }
            }

            mineSweeper.printBoard();

            if (mineSweeper.isGameOver()) {
                System.out.println("Congratulations! You found all the mines!");
            }

        }

    }

    /**
     * Game initialization includes setting mines and creating initial free space block
     */
    private void initGame() {
        //set mine counts
        System.out.print("How many mines do you want on the field? ");
        String input = scanner.nextLine();
        int mineCount;

        if (!input.matches("[0-9]+")) {
            System.out.println("Error! Invalid mine count given! Starting game with 10 mines.");
            mineCount = 10;
        } else {
            mineCount = Integer.parseInt(input);
        }

        mineSweeper.printBoard();

        //init board based on what cell user wants free
        System.out.print("Set/unset mines marks or claim a cell as free: ");
        String[] turnParameters = getUserTurn();
        int coordinateOne = Integer.parseInt(turnParameters[0]);
        int coordinateTwo = Integer.parseInt(turnParameters[1]);

        //randomly add mines to board
        if (turnParameters[2].equals("free")) {
            mineSweeper.populateMines(mineCount, coordinateOne, coordinateTwo, "free");
        } else {
            mineSweeper.populateMines(mineCount, coordinateOne, coordinateTwo, "mine");
        }

        //add numbers next to mines
        mineSweeper.populateMineCounts(coordinateOne, coordinateTwo);

        //set free space based on user input
        if ("free".equals(turnParameters[2])) {
            mineSweeper.explore(coordinateOne, coordinateTwo);
        } else {
            mineSweeper.exploreAroundMine();
            mineSweeper.setCell(State.MARKER, coordinateOne, coordinateTwo);
        }

        //print starting board to user
        mineSweeper.printBoard();

        if (mineSweeper.isGameOver()) {
            System.out.println("Congratulations! You found all the mines!");
        }
    }


    /**
     * gets coordinates from user and validates input for two valid ints
     *
     * @return  an array of size two of valid integer coordinates
     */
    private String[] getUserTurn() {
        String[] holder;
        int maxX = mineSweeper.getCols();
        int maxY = mineSweeper.getRows();

        while (true) {
            System.out.print("Set/unset mines marks or claim a cell as free: ");
            holder = scanner.nextLine().trim().split("\\s+");

            //make sure user entered two digits
            if (holder.length != 3) {
                System.out.println("Error! Must enter two integer coordinates and either 'free' or 'mine'. Try again!");
                continue;
            }

            if (!holder[0].matches("[0-9]+") || !holder[1].matches("[0-9]+")) {
                System.out.println("Error! Must enter two integer coordinates. Try again!");
                continue;
            }

            if (!holder[2].equals("free") && !holder[2].equals("mine")) {
                System.out.println("Error! Must enter either 'free' or 'mine' for third value. Try again");
                continue;
            }

            int coordinateOne = Integer.parseInt(holder[0]) - 1;
            int coordinateTwo = Integer.parseInt(holder[1]) - 1;

            if (coordinateOne < 0 || coordinateTwo < 0) {
                System.out.println("Error! Cannot have negative coordinates. Try again!");
                continue;
            }

            //make sure user entered coordinates in valid range
            if (coordinateOne > maxX) {
                System.out.println("Error! First coordinate must be less than " + maxX);
                continue;
            }

            if (coordinateTwo > maxY) {
                System.out.println("Error! Second coordinate must be less than " + maxY);
                continue;
            }

            holder[0] = "" + coordinateTwo;
            holder[1] = "" + coordinateOne;

            return holder;
        }

    }

}
