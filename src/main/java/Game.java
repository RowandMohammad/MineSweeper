import java.util.ArrayList;
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
            System.out.println("Choose action:");
            System.out.println("1 - Reveal");
            System.out.println("2 - Flag/Unflag");
            int action = in.nextInt();

            System.out.println("Enter your move (row col):");
            int myX = in.nextInt();
            int myY = in.nextInt();

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
                    //... [Rest of the code remains unchanged] ...
                } else {
                    expand(myX, myY);
                }
            }
        }
    }

    }
