package fr.chaffotm.quoridor.mapper;

import fr.chaffotm.quoridor.dto.GameDto;
import fr.chaffotm.quoridor.dto.PositionDto;
import fr.chaffotm.quoridor.model.Game;

import java.util.List;
import java.util.stream.Collectors;

public class GameMapper {

    public static GameDto map(Game game) {
        final List<PositionDto> pawns = game.getPawns().stream()
                .map(pawn -> PositionMapper.map(pawn.getPosition()))
                .collect(Collectors.toList());
        return new GameDto(game.getId(), BoardMapper.map(game.getBoard()), pawns);
    }

}
