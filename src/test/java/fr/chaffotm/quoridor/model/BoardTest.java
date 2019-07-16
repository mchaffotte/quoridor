package fr.chaffotm.quoridor.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class BoardTest {

    @Test
    @DisplayName("New board should create the smallest board, size = 3")
    public void newBoardShouldCreateTheSmallestBoard() {
        Board board = new Board(3);

        assertThat(board.getSize())
                .isEqualTo(3);
    }

    @Test
    @DisplayName("New board should throw an exception with an odd size")
    public void newBoardShouldThrowAnExceptionWithAnOddSize() {
        final Throwable throwable = catchThrowable(() -> new Board(4));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The board size must be an odd number");
    }

    @Test
    @DisplayName("New board should throw an exception with size less than 3")
    public void createGameShouldNotCreateGameWithSizeLessThanThree() {
        final Throwable throwable = catchThrowable(() -> new Board(1));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The board size must be at least 3");
    }

}
