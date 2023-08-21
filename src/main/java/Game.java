import java.util.ArrayList;
import java.util.Scanner;

class Game {
    private Board userBoard;
    private Board realBoard;
    private Mine mineManager;
    private int SIDES;
    private int MINES;
    private Scanner in = new Scanner(System.in);

    Game() {
        chooseMode();
    }

    private void chooseMode() {
        System.out.println("Welcome to Minesweeper Game");
        System.out.println("Select Mode");
        System.out.println("0 - Easy");
        System.out.println("1 - Medium");
        System.out.println("2 - Hard");

        int choice = in.nextInt();
        switch (choice) {
            case 1:
                SIDES = 16;
                MINES = 40;
                break;
            case 2:
                SIDES = 24;
                MINES = 99;
                break;
            default:
                SIDES = 9;
                MINES = 10;
        }
    }

    private void initializeRealBoard() {
        for (ArrayList<Integer> mineLocation : mineManager.getMinesLocation()) {
            realBoard.placeMine(mineLocation.get(0), mineLocation.get(1));
        }
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < SIDES && col >= 0 && col < SIDES;
    }



    private void placeCounts() {
        for (int i = 0; i < SIDES; i++) {
            for (int j = 0; j < SIDES; j++) {
                if (!realBoard.isMine(i, j)) {
                    int count = mineManager.getAdjacentMinesCount(i, j);
                    realBoard.setCell(i, j, Integer.toString(count));
                }
            }
        }
    }
}
