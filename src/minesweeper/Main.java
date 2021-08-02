package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MineSweeper mineSweeper = new MineSweeper(9, 9);
        GameController controller = new GameController(scanner, mineSweeper);
        controller.playGame();
    }
}
