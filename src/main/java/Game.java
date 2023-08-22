import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Game {
    private Board userBoard;
    private Board realBoard;
    private Mine mineManager;
    private int SIDES;
    private int MINES;
    private int movesLeft;
    private boolean isFirstMove = true;
    private Scanner in = new Scanner(System.in);

    Game() {
        chooseMode();
        userBoard = new Board(SIDES);
        realBoard = new Board(SIDES);
        movesLeft = SIDES * SIDES;
    }

    private void initializeRealBoard(int safeX, int safeY) {
        mineManager = new Mine(SIDES, MINES, safeX, safeY);  // pass the safe spot
        for (ArrayList<Integer> mineLocation : mineManager.getMinesLocation()) {
            realBoard.placeMine(mineLocation.get(0), mineLocation.get(1));
        }
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < SIDES && col >= 0 && col < SIDES;
    }

    private void expand(int row, int col) {
        if (!isValid(row, col)) return;
        if (realBoard.isMine(row, col)) return;
        if (!userBoard.getCell(row, col).equals("?")) return;

        movesLeft--;

        String data = realBoard.getCell(row, col);
        if (!data.equals("0")) {
            userBoard.setCell(row, col, data);
        } else {
            userBoard.setCell(row, col, " ");
            for (int r = row - 1; r <= row + 1; r++) {
                for (int c = col - 1; c <= col + 1; c++) {
                    if (r != row || c != col) {
                        expand(r, c);
                    }
                }
            }
        }
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

    private void chooseMode() {
        System.out.println("Welcome to Minesweeper Game");
        System.out.println("Select Mode");
        System.out.println("0 - Easy");
        System.out.println("1 - Medium");
        System.out.println("2 - Hard");

        while (true) {
            String input = in.nextLine(); // Read the whole line
            switch (input.trim()) {
                case "1":
                    SIDES = 16;
                    MINES = 40;
                    return; // exit the loop
                case "2":
                    SIDES = 24;
                    MINES = 99;
                    return; // exit the loop
                case "0":
                    SIDES = 9;
                    MINES = 10;
                    return; // exit the loop
                default:
                    System.out.println("Invalid choice. Please select 0, 1, or 2.");
            }
        }
    }



    private boolean allMinesFlagged() {
        for (ArrayList<Integer> mineLocation : mineManager.getMinesLocation()) {
            if (!userBoard.isFlagged(mineLocation.get(0), mineLocation.get(1))) {
                return false;
            }
        }
        return true;
    }



    public void playMineSweeper() {
        boolean gameOver = false;

        while (!gameOver) {
            if (movesLeft == MINES && allMinesFlagged() && !gameOver) {
                userBoard.printBoard();
                System.out.println("You Won!!!");
                break;
            }

            System.out.println("Moves left:" + (movesLeft - MINES));
            userBoard.printBoard();

            int action = -1;
            do {
                try {
                    System.out.println("Choose action:");
                    System.out.println("1 - Reveal");
                    System.out.println("2 - Flag/Unflag");
                    action = in.nextInt();

                    if(action != 1 && action != 2) {
                        System.out.println("Please select a valid action (1 or 2).");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please select 1 or 2.");
                    in.next(); // Clear the invalid input
                }
            } while (action != 1 && action != 2);

            int myX = -1, myY = -1;
            boolean validMove = false;
            while (!validMove) {
                try {
                    System.out.println("Enter your move (row col):");
                    myX = in.nextInt();
                    myY = in.nextInt();

                    if (isValid(myX, myY)) {
                        validMove = true;
                    } else {
                        System.out.println("Invalid move! Coordinates out of bounds. Try again.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid move! Please enter numeric row and col values.");
                    in.next(); // Clear the invalid input
                }
            }

            if (action == 2) {
                userBoard.flagCell(myX, myY);
                continue;
            }

            if (userBoard.isFlagged(myX, myY)) {
                System.out.println("Cell is flagged! Unflag it first to reveal.");
                continue;
            }

            if (isFirstMove) {
                isFirstMove = false;
                // Initialize the board using myX and myY as the safe spot
                initializeRealBoard(myX, myY);
                placeCounts();
            }

            if (userBoard.getCell(myX, myY).equals("?")) {
                if (realBoard.isMine(myX, myY)) {
                    userBoard.printBoard();
                    System.out.println("Oops! You stepped on a mine! Game Over.");
                    gameOver = true;  // Add this to signify the game has ended after hitting a mine.
                } else {
                    expand(myX, myY);
                }
            }
        }
    }


    public Board getUserBoard() {
        return userBoard;
    }
}
