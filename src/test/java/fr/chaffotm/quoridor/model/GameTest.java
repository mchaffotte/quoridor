package fr.chaffotm.quoridor.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class GameTest {

    @Test
    @DisplayName("Move the pawn should move the pawn to one square to the east")
    public void movePawnShouldMoveToTheEast() {
        //Given
        Game game = new Game(3);
        //When
        game.movePawn(new Position(1, 1));
        //Then
        assertThat(game.getPawns().get(0).getPosition()).isEqualTo(new Position(1, 1));
    }

    @Test
    @DisplayName("Move the pawn should move the pawn to one square to the north")
    public void movePawnShouldMoveToTheNorth() {
        //Given
        Game game = new Game(3);
        //When
        game.movePawn(new Position(0, 0));
        //Then
        assertThat(game.getPawns().get(0).getPosition()).isEqualTo(new Position(0, 0));
    }

    @Test
    @DisplayName("Move the pawn should move the pawn to one square to the south")
    public void movePawnShouldMoveToTheSouth() {
        //Given
        Game game = new Game(3);
        //When
        game.movePawn(new Position(0, 2));
        //Then
        assertThat(game.getPawns().get(0).getPosition()).isEqualTo(new Position(0, 2));
    }

    @Test
    @DisplayName("Move the pawn should move the pawn to one square to the west")
    public void movePawnShouldMoveToTheWest() {
        //Given
        Game game = new Game(3);
        game.movePawn(new Position(1, 1));
        //When
        game.movePawn(new Position(0, 1));
        //Then
        assertThat(game.getPawns().get(0).getPosition()).isEqualTo(new Position(0, 1));
    }

    @Test
    @DisplayName("Move the pawn should not move the pawn out of the board")
    public void movePawnShouldNotMoveThePawnOutOfTheBoard() {
        //Given
        Game game = new Game(3);
        //When
        final Throwable throwable = catchThrowable(() -> game.movePawn(new Position(-1, 1)));
        //Then
        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("The new position is not inside the board");
    }

    @Test
    @DisplayName("Move the pawn should not move the pawn to an unreachable position")
    public void movePawnShouldNotMoveToAnUnreachablePosition() {
        //Given
        Game game = new Game(3);
        //When
        final Throwable throwable = catchThrowable(() -> game.movePawn(new Position(2, 2)));
        //Then
        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("It is not possible to move to (2, 2)");
    }

    @Test
    @DisplayName("Move the pawn should not move on another pawn")
    public void movePawnShouldNotMoveOnAnotherPawn() {
        //Given
        Game game = new Game(3);
        game.movePawn(new Position(1, 1));
        //When
        final Throwable throwable = catchThrowable(() -> game.movePawn(new Position(2, 1)));
        //Then
        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("It is not possible to move to (2, 1)");
    }

    @Test
    @DisplayName("Is over should not be over when the pawn is not on its goal line")
    public void isOverShouldNotBeOverWhenThePawnInNotOnItsGoalLine() {
        //Given
        Game game = new Game(3);
        //When
        game.movePawn(new Position(1, 1));
        //Then
        assertThat(game.isOver()).isFalse();
    }

    @Test
    @DisplayName("Is over should not be over when the pawn is not on its goal line")
    public void isOverShouldBeOverWhenThePawnInOnItsGoalLine() {
        //Given
        Game game = new Game(3);
        game.movePawn(new Position(1, 1));
        game.movePawn(new Position(1, 0));
        //When
        game.movePawn(new Position(2, 0));
        //Then
        assertThat(game.isOver()).isTrue();
    }

    @Test
    @DisplayName("Move pawn should not be possible when the game is over")
    public void movePawnShouldNotBePossibleWhenTheGameIsOver() {
        //Given
        Game game = new Game(3);
        game.movePawn(new Position(1, 1));
        game.movePawn(new Position(1, 0));
        game.movePawn(new Position(2, 0));
        //When
        final Throwable throwable = catchThrowable(() -> game.movePawn(new Position(2, 1)));
        //Then
        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("The game is over");
    }

    @Test
    @DisplayName("Get movement possibilities should be empty when the game is over")
    public void getMovementPossibilitiesShouldBeEmptyWhenTheGameIsOver() {
        //Given
        Game game = new Game(3);
        game.movePawn(new Position(1, 1));
        game.movePawn(new Position(1, 0));
        game.movePawn(new Position(2, 0));
        //When
        Set<Position> possibilities = game.getMovementPossibilities();
        //Then
        assertThat(possibilities).isEmpty();
    }

    @Test
    @DisplayName("Get movement possibilities should give all the possible movements of the pawn")
    public void getMovementPossibilitiesShouldGiveAllThePossibleMovementsOfThePawn() {
        //Given
        Game game = new Game(5);
        game.movePawn(new Position(1, 2));
        //When
        Set<Position> possibilities = game.getMovementPossibilities();
        //Then
        assertThat(possibilities).containsExactlyInAnyOrder(
                new Position(1,  1),
                new Position(2, 2),
                new Position(1, 3),
                new Position(0, 2)
        );
    }

    @Test
    @DisplayName("Get movement possibilities should give the possible movements of the pawn minus out of the board")
    public void getMovementPossibilitiesShouldGiveThePossibleMovementsOfThePawnMinusOutOfTheBoard() {
        //Given
        Game game = new Game(5);
        //When
        Set<Position> possibilities = game.getMovementPossibilities();
        //Then
        assertThat(possibilities).containsExactlyInAnyOrder(
                new Position(0, 1),
                new Position(1, 2),
                new Position(0, 3)
        );
    }

    @Test
    @DisplayName("Get movement possibilities should give the possible movements of the pawn minus opponent")
    public void getMovementPossibilitiesShouldGiveThePossibleMovementsOfThePawnMinusOpponent() {
        //Given
        Game game = new Game(3);
        game.movePawn(new Position(1, 1));
        //When
        Set<Position> possibilities = game.getMovementPossibilities();
        //Then
        assertThat(possibilities).containsExactlyInAnyOrder(
                new Position(1, 0),
                new Position(1, 2),
                new Position(0, 1)
        );
    }
}