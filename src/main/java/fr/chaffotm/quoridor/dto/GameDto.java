package fr.chaffotm.quoridor.dto;

import java.util.List;

public class GameDto {

    private Long id;

    private BoardDto board;

    private List<PositionDto> pawns;

    protected GameDto() {
    }

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
