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

    public int getSize() {
        return board.size();
    }
}
