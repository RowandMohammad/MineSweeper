import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Game {
    private Board userBoard;
    private Board realBoard;
    private Mine mineManager;
    private int sides;
    private int mines;
    private int movesLeft;
    private boolean isFirstMove = true;
    private Scanner in = new Scanner(System.in);

    Game() {
        chooseMode();
        userBoard = new Board(sides);
        realBoard = new Board(sides);
        movesLeft = sides * sides;
    }

    private void initializeRealBoard(int safeX, int safeY) {
        mineManager = new Mine(sides, mines, safeX, safeY);  // pass the safe spot
        for (ArrayList<Integer> mineLocation : mineManager.getMinesLocation()) {
            realBoard.placeMine(mineLocation.get(0), mineLocation.get(1));
        }
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < sides && col >= 0 && col < sides;
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
        for (int i = 0; i < sides; i++) {
            for (int j = 0; j < sides; j++) {
                if (!realBoard.isMine(i, j)) {
                    int count = mineManager.getAdjacentMinesCount(i, j);
                    realBoard.setCell(i, j, Integer.toString(count));
                }
            }
        }
    }

    private void chooseMode() {
        System.err.println("Welcome to Minesweeper Game");
        System.err.println("Select Mode");
        System.err.println("0 - Easy");
        System.err.println("1 - Medium");
        System.err.println("2 - Hard");

        while (true) {
            String input = in.nextLine(); // Read the whole line
            switch (input.trim()) {
                case "1":
                    sides = 16;
                    mines = 40;
                    return; // exit the loop
                case "2":
                    sides = 24;
                    mines = 99;
                    return; // exit the loop
                case "0":
                    sides = 9;
                    mines = 10;
                    return; // exit the loop
                default:
                    System.err.println("Invalid choice. Please select 0, 1, or 2.");
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



    private boolean checkWinCondition() {
        if (movesLeft == mines && allMinesFlagged()) {
            userBoard.printBoard();
            System.err.println("You Won!!!");
            return true;
        }
        return false;
    }

    private void displayMovesLeftAndBoard() {
        System.err.println("Moves left:" + (movesLeft - mines));
        userBoard.printBoard();
    }

    private int promptActionChoice() {
        int action;
        do {
            System.err.println("Choose action:");
            System.err.println("1 - Reveal");
            System.err.println("2 - Flag/Unflag");
            try {
                action = in.nextInt();
                if (action != 1 && action != 2) {
                    System.err.println("Please select a valid action (1 or 2).");
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input! Please select 1 or 2.");
                in.next();
                action = -1;
            }
        } while (action != 1 && action != 2);
        return action;
    }

    private int[] promptMoveCoordinates() {
        int x;
        int y;
        while (true) {
            try {
                System.out.println("Enter your move (row col):");
                x = in.nextInt();
                y = in.nextInt();
                if (isValid(x, y)) {
                    return new int[]{x, y};
                } else {
                    System.err.println("Invalid move! Coordinates out of bounds. Try again.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid move! Please enter numeric row and col values.");
                in.next();
            }
        }
    }

    private void handleActionOnCell(int action, int[] move) {
        if (action == 2) {
            userBoard.flagCell(move[0], move[1]);
            return;
        }

        if (userBoard.isFlagged(move[0], move[1])) {
            System.err.println("Cell is flagged! Unflag it first to reveal.");
            return;
        }

        if (isFirstMove) {
            isFirstMove = false;
            initializeRealBoard(move[0], move[1]);
            placeCounts();
        }
    }

    private boolean processCellReveal(int x, int y) {
        if (userBoard.getCell(x, y).equals("?")) {
            if (realBoard.isMine(x, y)) {
                userBoard.printBoard();
                System.out.println("Oops! You stepped on a mine! Game Over.");
                return true;
            } else {
                expand(x, y);
            }
        }
        return false;
    }

    public void playMineSweeper() {
        boolean gameOver = false;

        while (!gameOver) {
            if (checkWinCondition()) {
                break;
            }

            displayMovesLeftAndBoard();

            int action = promptActionChoice();
            int[] move = promptMoveCoordinates();

            handleActionOnCell(action, move);

            gameOver = processCellReveal(move[0], move[1]);
        }
    }


    public Board getUserBoard() {
        return userBoard;
    }

    public Board getRealBoard() {
        return realBoard;
    }
}









