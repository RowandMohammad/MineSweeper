import java.util.ArrayList;

class Board {
    private ArrayList<ArrayList<String>> board;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";


    Board(int sides) {
        board = new ArrayList<>();
        for (int i = 0; i < sides; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j < sides; j++) {
                board.get(i).add("?");
            }
        }
    }

    public void initialize() {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(i).size(); j++) {
                board.get(i).set(j, "?");
            }
        }
    }

    public void flagCell(int row, int col) {
        if (isValid(row, col)) {
            if (board.get(row).get(col).equals("?")) {
                board.get(row).set(col, "F");
            } else if (board.get(row).get(col).equals("F")) {
                board.get(row).set(col, "?");
            }
        } else {
            System.err.println("Invalid coordinates! Can't flag this cell.");
        }
    }

    // Add this method to the Board class for bounds checking.
    private boolean isValid(int row, int col) {
        return row >= 0 && row < board.size() && col >= 0 && col < board.get(0).size();
    }


    public boolean isFlagged(int row, int col) {
        return "F".equals(board.get(row).get(col));
    }


    public boolean isMine(int row, int col) {
        return "*".equals(board.get(row).get(col));
    }

    public void placeMine(int row, int col) {
        board.get(row).set(col, "*");
    }

    public void setCell(int row, int col, String value) {
        board.get(row).set(col, value);
    }

    public String getCell(int row, int col) {
        return board.get(row).get(col);
    }

    public void printBoard() {
        System.err.println();

        // Print header with column numbers
        System.err.print("    "); // Initial spacing to align with row numbers
        for (int i = 0; i < board.size(); i++) {
            if (i < 10) {
                System.err.print(ANSI_CYAN + " " + i + " " + ANSI_RESET);
            } else {
                System.err.print(ANSI_CYAN + i + " " + ANSI_RESET);
            }
        }
        System.err.println();

        for (int i = 0; i < board.size(); i++) {
            // Print row number with proper spacing
            if (i < 10) {
                System.err.print(ANSI_CYAN + i + "   " + ANSI_RESET);
            } else {
                System.err.print(ANSI_CYAN + i + "  " + ANSI_RESET);
            }
            for (int j = 0; j < board.get(i).size(); j++) {
                String cellValue = board.get(i).get(j);
                System.err.print("|");  // this creates the grid effect
                switch (cellValue) {
                    case "1":
                        System.err.print(ANSI_BLUE + cellValue + " " + ANSI_RESET);
                        break;
                    case "2":
                        System.err.print(ANSI_GREEN + cellValue + " " + ANSI_RESET);
                        break;
                    case "3":
                        System.err.print(ANSI_YELLOW + cellValue + " " + ANSI_RESET);
                        break;
                    case "4":
                    case "5":
                    case "6":
                        System.err.print(ANSI_RED + cellValue + " " + ANSI_RESET);
                        break;
                    case "F":
                        System.err.print(ANSI_PURPLE + cellValue + " " + ANSI_RESET);
                        break;
                    case "*":
                        System.err.print(ANSI_BLACK + cellValue + " " + ANSI_RESET);
                        break;
                    default:
                        System.err.print(cellValue + " ");
                }
            }
            System.err.println("|");  // this creates the grid effect for the last cell
        }
    }




    public int getSize() {
        return board.size();
    }
}
