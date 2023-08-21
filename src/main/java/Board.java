import java.util.ArrayList;

class Board {
    private ArrayList<ArrayList<String>> board;

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
        System.out.println();
        System.out.print("   ");
        for (int i = 0; i < board.size(); i++) {
            if (i < 10) {
                System.out.print(" ");
            }
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < board.size(); i++) {
            System.out.print(i + "  ");
            if (i < 10) {
                System.out.print(" ");
            }
            for (int j = 0; j < board.get(i).size(); j++) {
                if ("9".equals(board.get(i).get(j))) {
                    System.out.print("   ");
                } else {
                    System.out.print(board.get(i).get(j) + "  ");
                }
            }
            System.out.println();
        }
    }

    public int getSize() {
        return board.size();
    }
}
