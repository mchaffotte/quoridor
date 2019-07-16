package fr.chaffotm.quoridor.mapper;

import fr.chaffotm.quoridor.dto.PositionDto;
import fr.chaffotm.quoridor.model.Position;

import java.util.List;
import java.util.stream.Collectors;

public class PositionMapper {

    public static PositionDto map(final Position position) {
        return new PositionDto(position.getColumn(), position.getRow());
    }

    public static List<PositionDto> map(final List<Position> positions) {
        return positions.stream()
                .map(PositionMapper::map)
                .collect(Collectors.toList());
    }

}
