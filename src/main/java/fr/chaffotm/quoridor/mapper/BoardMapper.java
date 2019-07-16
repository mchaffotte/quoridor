package fr.chaffotm.quoridor.mapper;

import fr.chaffotm.quoridor.dto.BoardDto;
import fr.chaffotm.quoridor.model.Board;

public class BoardMapper {

    public static BoardDto map(final Board board) {
        return new BoardDto(board.getSize());
    }

}
