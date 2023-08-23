import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    @ParameterizedTest
    @MethodSource("gameModeProvider")
    void testChooseMode(String input, int expectedSize) {
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(in);

        Game game = new Game();
        int boardSize = game.getUserBoard().getSize();

        assertEquals(expectedSize, boardSize);
    }

    private static Stream<Arguments> gameModeProvider() {
        return Stream.of(
                Arguments.of("0\n", 9), // Easy mode.
                Arguments.of("1\n", 16), // Medium mode.
                Arguments.of("2\n", 24), // Hard mode.
                Arguments.of("3\n0\n", 9), // Invalid choice then Easy mode.
                Arguments.of("3\n1\n", 16) // Invalid choice then Medium mode.
        );
    }



}
