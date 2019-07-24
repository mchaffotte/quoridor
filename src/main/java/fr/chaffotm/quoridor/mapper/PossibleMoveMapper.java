package fr.chaffotm.quoridor.mapper;

import fr.chaffotm.quoridor.dto.MovementPossibilitiesDto;
import fr.chaffotm.quoridor.model.Position;

import java.util.Set;

public class PossibleMoveMapper {

    public static MovementPossibilitiesDto map(final Set<Position> possibilities) {
        return new MovementPossibilitiesDto(PositionMapper.map(possibilities));
    }

}
