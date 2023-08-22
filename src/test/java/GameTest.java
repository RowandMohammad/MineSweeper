import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    @Test
    public void testChooseModeEasy() {
        String input = "0\n";  // Simulate user entering "0" for Easy mode.
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(in);  // Redirect System.in to use our input stream.

        Game game = new Game();
        int boardSize = game.getUserBoard().getSize();  // Here, we assume you've added a getUserBoard() method to expose userBoard.

        assertEquals(9, boardSize);  // Easy mode should have a board size of 9.
    }

    @Test
    public void testChooseModeMedium() {
        String input = "1\n";  // Simulate user entering "1" for Medium mode.
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(in);

        Game game = new Game();
        int boardSize = game.getUserBoard().getSize();

        assertEquals(16, boardSize);  // Medium mode should have a board size of 16.
    }


    @Test
    public void testChooseModeHard() {
        String input = "2\n";  // Simulate user entering "2" for Hard mode.
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(in);

        Game game = new Game();
        int boardSize = game.getUserBoard().getSize();

        assertEquals(24, boardSize);  // Hard mode should have a board size of 25.
    }

    @Test
    public void testInvalidChoiceThenEasy() {
        String input = "3\n0\n";  // Simulate user entering "3" (invalid) followed by "0" for Easy mode.
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(in);

        Game game = new Game();
        int boardSize = game.getUserBoard().getSize();

        assertEquals(9, boardSize);  // Should eventually choose Easy mode with a board size of 9.
    }



}
