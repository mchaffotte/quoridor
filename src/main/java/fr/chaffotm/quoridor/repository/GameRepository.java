package fr.chaffotm.quoridor.repository;

import fr.chaffotm.quoridor.model.Game;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GameRepository extends PagingAndSortingRepository<Game, Long> {

}
