package fr.chaffotm.quoridor.model;

import javax.persistence.*;

@Entity
public class Board {

    public static final int DEFAULT_SIZE = 9;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_generator")
    @SequenceGenerator(name="board_generator", sequenceName = "board_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private int size;

    protected Board() {
        // used by JPA
    }

    public Board(final int boardSize) {
        if (boardSize % 2 == 0) {
            throw new IllegalArgumentException("The board size must be an odd number");
        }
        if (boardSize < 3 ) {
            throw new IllegalArgumentException("The board size must be at least 3");
        }
        size = boardSize;
    }

    public int getSize() {
        return size;
    }

}
