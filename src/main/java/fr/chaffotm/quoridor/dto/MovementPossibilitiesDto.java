package fr.chaffotm.quoridor.dto;

import java.util.Set;

public class MovementPossibilitiesDto {

    private Set<PositionDto> possibilities;

    protected MovementPossibilitiesDto() {
    }

    public MovementPossibilitiesDto(Set<PositionDto> possibilities) {
        this.possibilities = possibilities;
    }

    public Set<PositionDto> getPossibilities() {
        return possibilities;
    }

}
