package fr.chaffotm.quoridor.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_generator")
    @SequenceGenerator(name="game_generator", sequenceName = "game_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Board board;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Pawn> pawns;

    private boolean over;

    public Game() {
        //Used by JPA
    }

    public Game(int boardSize) {
        board = new Board(boardSize);
        final int lineCenter = (boardSize - 1) / 2;
        pawns = List.of(
                new Pawn(0, lineCenter),
                new Pawn(boardSize - 1, lineCenter)
        );
        over = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Pawn> getPawns() {
        return pawns;
    }

    public void setPawns(List<Pawn> pawns) {
        this.pawns = pawns;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public void movePawn(final Position to) {
        if (over) {
            throw new IllegalStateException("The game is over");
        }
        if (!board.isInside(to)) {
            throw new IllegalStateException("The new position is not inside the board");
        }
        final Set<Position> moves = getMovementPossibilities();
        if (!containsPosition(moves, to)) {
            throw new IllegalStateException("It is not possible to move to (" + to.getColumn() + ", " + to.getRow() + ")");
        }
        pawns.get(0).setPosition(to);
        over = pawns.get(0).getPosition().getColumn() == board.getSize() - 1;
    }

    private boolean containsPosition(Set<Position> moves, Position position) {
        for (Position move : moves) {
            if (move.getColumn() == position.getColumn() && move.getRow() == position.getRow()) {
                return true;
            }
        }
        return false;
    }

    public Set<Position> getMovementPossibilities() {
        final Set<Position> positions = new HashSet<>();
        if (over) {
            return positions;
        }
        final Position from = pawns.get(0).getPosition();
        final List<Move> moves = List.of(
                new Move(from, 0, -1),
                new Move(from, 1, 0),
                new Move(from, 0, 1),
                new Move(from, -1, 0)
        );
        for (Move move : moves) {
            if (getPositionState(move.getTo()) == PositionState.FREE) {
                positions.add(move.getTo());
            }
        }
        return positions;
    }

    private PositionState getPositionState(Position to) {
        if (!board.isInside(to)) {
            return PositionState.OUTSIDE;
        }
        if (hasPawnOn(to)) {
            return PositionState.PAWN;
        }
        return PositionState.FREE;
    }

    private boolean hasPawnOn(final Position position) {
        return containsPosition(pawns, position);
    }

    private boolean containsPosition(List<Pawn> pawns, Position position) {
        for (Pawn move : pawns) {
            if (move.getPosition().getColumn() == position.getColumn() && move.getPosition().getRow() == position.getRow()) {
                return true;
            }
        }
        return false;
    }

}
