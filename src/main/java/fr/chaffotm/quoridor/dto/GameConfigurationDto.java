package fr.chaffotm.quoridor.dto;

public class GameConfigurationDto {

    private int boardSize;

    private int numberOfFencesPerPlayer;

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public int getNumberOfFencesPerPlayer() {
        return numberOfFencesPerPlayer;
    }

    public void setNumberOfFencesPerPlayer(int numberOfFencesPerPlayer) {
        this.numberOfFencesPerPlayer = numberOfFencesPerPlayer;
    }

}
