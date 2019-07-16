package fr.chaffotm.quoridor.controller;

import fr.chaffotm.quoridor.dto.GameDto;
import fr.chaffotm.quoridor.dto.GameConfigurationDto;
import fr.chaffotm.quoridor.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("games")
public class GameRestController {

    private final GameService gameService;

    public GameRestController(final GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody(required = false) @Valid final GameConfigurationDto configuration) {
        final long countryId = gameService.create(configuration);
        final URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/games/{id}").build()
                .expand(countryId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public GameDto get(@PathVariable("id") final long id) {
        return gameService.get(id);
    }
}
