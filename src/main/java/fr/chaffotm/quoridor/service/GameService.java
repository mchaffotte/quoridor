package fr.chaffotm.quoridor.service;

import fr.chaffotm.quoridor.dto.GameDto;
import fr.chaffotm.quoridor.dto.GameConfigurationDto;
import fr.chaffotm.quoridor.mapper.GameMapper;
import fr.chaffotm.quoridor.model.Board;
import fr.chaffotm.quoridor.model.Game;
import fr.chaffotm.quoridor.repository.GameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional
public class GameService {

    private final GameRepository gameRepository;

    public GameService(final GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public long create(final GameConfigurationDto configuration) {
        final int boardSize;
        if (configuration == null) {
            boardSize = Board.DEFAULT_SIZE;
        } else {
            boardSize = configuration.getBoardSize();
        }
        final Game game = new Game(boardSize);
        gameRepository.save(game);
        return game.getId();
    }

    public GameDto get(final long id) {
        Optional<Game> game = gameRepository.findById(id);
        if (game.isEmpty()) {
            throw new EntityNotFoundException("Game not found");
        }
        return GameMapper.map(game.get());
    }

}
