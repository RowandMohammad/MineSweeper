import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    @DisplayName("Testing Flagging")
    void testFlagCell() {
        Board board = new Board(5);
        board.flagCell(2, 2);
        assertEquals("F", board.getCell(2, 2));

        board.flagCell(2, 2);  // Should remove the flag
        assertEquals("?", board.getCell(2, 2));
    }

    @Test
    @DisplayName("Testing Flag Check")
    void testIsFlagged() {
        Board board = new Board(5);
        assertFalse(board.isFlagged(2, 2));

        board.flagCell(2, 2);
        assertTrue(board.isFlagged(2, 2));
    }

    @Test
    @DisplayName("Testing Mine Check")

    void testIsMine() {
        Board board = new Board(5);
        assertFalse(board.isMine(2, 2));

        board.placeMine(2, 2);
        assertTrue(board.isMine(2, 2));
    }

    @Test
    @DisplayName("Testing Setting Cell")

    void testSetCell() {
        Board board = new Board(5);
        board.setCell(2, 2, "1");
        assertEquals("1", board.getCell(2, 2));
    }

}