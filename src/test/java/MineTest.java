import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MineTest {

    @Test
    @DisplayName("Testing if I can place a mine")
    void testCanPlaceMine() {
        Mine mine = new Mine(5, 5, 2, 2);
        assertFalse(mine.canPlaceMine(2, 2, 2, 2));
        assertFalse(mine.canPlaceMine(1, 2, 2, 2));
        assertTrue(mine.canPlaceMine(4, 4, 2, 2));
    }

    @Test
    @DisplayName("Testing where I placed a mine")
    void testIsMineAt() {
        Mine mine = new Mine(5, 5, 2, 2);
        ArrayList<ArrayList<Integer>> mineLocations = mine.getMinesLocation();
        int x = mineLocations.get(0).get(0);
        int y = mineLocations.get(0).get(1);
        assertTrue(mine.isMineAt(x, y));
    }

}
