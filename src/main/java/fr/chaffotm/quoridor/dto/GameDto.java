package fr.chaffotm.quoridor.dto;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class GameDto {

    private final Long id;

    private final BoardDto board;

    private final List<PositionDto> pawns;

    public GameDto(Long id, BoardDto board, List<PositionDto> pawns) {
        this.id = id;
        this.board = board;
        this.pawns = pawns;
    }

    public Long getId() {
        return id;
    }

    public BoardDto getBoard() {
        return board;
    }

    public List<PositionDto> getPawns() {
        return pawns;
    }

}
