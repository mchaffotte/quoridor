package fr.chaffotm.quoridor.mapper;

import fr.chaffotm.quoridor.dto.PositionDto;
import fr.chaffotm.quoridor.model.Position;

import java.util.Set;
import java.util.stream.Collectors;

public class PositionMapper {

    public static PositionDto map(final Position position) {
        return new PositionDto(position.getColumn(), position.getRow());
    }

    public static Set<PositionDto> map(final Set<Position> positions) {
        return positions.stream()
                .map(PositionMapper::map)
                .collect(Collectors.toSet());
    }

    public static Position unmap(PositionDto position) {
        return new Position(position.getColumn(), position.getRow());
    }

}
