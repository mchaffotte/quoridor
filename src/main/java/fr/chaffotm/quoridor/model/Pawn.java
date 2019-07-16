package fr.chaffotm.quoridor.model;

import javax.persistence.*;

@Entity
public class Pawn {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pawn_generator")
    @SequenceGenerator(name="pawn_generator", sequenceName = "pawn_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Position position;

    public Pawn() {
    }

    public Pawn(final Position position) {
        this(position.getColumn(), position.getRow());
    }

    public Pawn(final int column, final int row) {
        this.position = new Position(column, row);
    }


    public Position getPosition() {
        return position;
    }

}
