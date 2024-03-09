import java.util.ArrayList;
import java.util.Random;

class Mine {
    private ArrayList<ArrayList<Integer>> minesLocation;

    Mine(int sides, int mines) {
        minesLocation = new ArrayList<>();
        getRandomMines(sides, mines);
    }

    private void getRandomMines(int sides, int mines) {
        boolean[] mark = new boolean[sides * sides];
        for (int j = 0; j < mines; j++) {
            minesLocation.add(new ArrayList<Integer>());
        }
        int i = 0;
        Random rn = new Random();
        while (i < mines) {
            int random = Math.abs(rn.nextInt()) % (sides * sides);
            int x = (random / sides) % sides;
            int y = random % sides;
            if (mark[random] == false) {
                ArrayList<Integer> mineCoordinates = new ArrayList<>();
                mineCoordinates.add(x);
                mineCoordinates.add(y);
                minesLocation.set(i, mineCoordinates);
                mark[random] = true;
                i++;
            }
        }
    }

    public int getAdjacentMinesCount(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!(i == 0 && j == 0) && isMineAt(x + i, y + j)) {
                    count++;
                }
            }
        }
        return count;
    }


    public ArrayList<ArrayList<Integer>> getMinesLocation() {
        return minesLocation;
    }

    public boolean isMineAt(int x, int y) {
        for (ArrayList<Integer> mineCoord : minesLocation) {
            if (mineCoord.get(0) == x && mineCoord.get(1) == y) {
                return true;
            }
        }
        return false;
    }


}