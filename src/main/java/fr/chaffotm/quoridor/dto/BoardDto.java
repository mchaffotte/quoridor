package fr.chaffotm.quoridor.dto;

import java.util.ArrayList;
import java.util.List;

public class BoardDto {

    private int size;

    private List<PositionDto> squares;

    public BoardDto() {
        // Used by JAX-RS
    }

    public BoardDto(int boardSize) {
        size = boardSize;
        squares = new ArrayList<>();
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                squares.add(new PositionDto(column, row));
            }
        }
    }

    public int getSize() {
        return size;
    }

    public List<PositionDto> getSquares() {
        return squares;
    }

}
