package fr.chaffotm.quoridor.model;

import javax.persistence.*;

@Entity
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "position_generator")
    @SequenceGenerator(name="position_generator", sequenceName = "position_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private int column;

    @Column(name = "r")
    private int row;

    protected Position() {
        // used by JPA
    }

    public Position(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

}
