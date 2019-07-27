package fr.chaffotm.quoridor;

import fr.chaffotm.quoridor.controller.error.BadRequestBody;
import fr.chaffotm.quoridor.controller.error.ErrorBody;
import fr.chaffotm.quoridor.dto.GameDto;
import fr.chaffotm.quoridor.dto.MovementPossibilitiesDto;
import fr.chaffotm.quoridor.dto.PositionDto;
import fr.chaffotm.quoridor.interceptor.JsonInterceptor;
import fr.chaffotm.quoridor.interceptor.LoggingInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import fr.chaffotm.quoridor.dto.GameConfigurationDto;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameRestControllerITest {

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        restTemplate.getRestTemplate().setInterceptors(
                List.of(new LoggingInterceptor(), new JsonInterceptor())
        );
    }

    @Test
    @DisplayName("Create game should create the game with default configuration")
    public void createGameShouldCreateTheGameWithDefaultConfiguration() {
        final ResponseEntity<Void> response = restTemplate.postForEntity("/games", null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        final URI location = response.getHeaders().getLocation();

        final ResponseEntity<GameDto> responseEntity = restTemplate.getForEntity(location, GameDto.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final GameDto game = responseEntity.getBody();
        assertThat(game).isNotNull();
        assertThat(game.getBoard().getSquares())
                .hasSize(81);
    }

    @Test
    @DisplayName("Create game should create the game with given configuration")
    public void createGameShouldCreateTheGame() {
        final GameConfigurationDto gameConfiguration = new GameConfigurationDto();
        gameConfiguration.setBoardSize(9);
        gameConfiguration.setNumberOfFencesPerPlayer(10);
        final ResponseEntity<Void> response = restTemplate.postForEntity("/games", gameConfiguration, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        final URI location = response.getHeaders().getLocation();

        final ResponseEntity<GameDto> responseEntity = restTemplate.getForEntity(location, GameDto.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final GameDto game = responseEntity.getBody();
        assertThat(game).isNotNull();
        assertThat(game.getBoard().getSquares())
                .hasSize(81);
    }

    @Test
    @DisplayName("Create game should not create the game with a misconfiguration")
    public void createGameShouldNotCreateGameWithOddNumber() {
        final GameConfigurationDto gameConfiguration = new GameConfigurationDto();
        gameConfiguration.setBoardSize(2);
        gameConfiguration.setNumberOfFencesPerPlayer(10);
        final BadRequestBody errorBody = new BadRequestBody();
        errorBody.addMessage("The board size must be an odd number");

        final ResponseEntity<BadRequestBody> response = restTemplate.postForEntity("/games", gameConfiguration, BadRequestBody.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo(errorBody);
    }

    @Test
    @DisplayName("Get game should not find an unknown game")
    public void getGameShouldNotFoundAnUnknownGame() {
        final ResponseEntity<ErrorBody> response = restTemplate.getForEntity("/games/4684644", ErrorBody.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @DisplayName("Move pawn should move the pawn and change the movement possibilities")
    public void movePawnShouldMoveThePawnAndChangeTheMovementPossibilities() {
        final ResponseEntity<Void> response = restTemplate.postForEntity("/games", null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        final URI location = response.getHeaders().getLocation();

        final ResponseEntity<GameDto> responseEntity = restTemplate.getForEntity(location, GameDto.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final GameDto game = responseEntity.getBody();

        ResponseEntity<MovementPossibilitiesDto> possibleResponse = restTemplate.getForEntity("/games/" + game.getId() + "/move-pawn/possibilities", MovementPossibilitiesDto.class);
        assertThat(possibleResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        MovementPossibilitiesDto movements = possibleResponse.getBody();

        assertThat(movements.getPossibilities())
                .containsExactlyInAnyOrder(
                        new PositionDto(0, 3),
                        new PositionDto(1, 4),
                        new PositionDto(0, 5)
                        );

        final ResponseEntity<GameDto> exchange = restTemplate.exchange("/games/" + game.getId() + "/move-pawn/", HttpMethod.PUT,  new HttpEntity<>(new PositionDto(1, 4)), GameDto.class);
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        final GameDto newGame = exchange.getBody();
        assertThat(newGame.getPawns().get(0)).isEqualTo( new PositionDto(1, 4));

        possibleResponse = restTemplate.getForEntity("/games/" + game.getId() + "/move-pawn/possibilities", MovementPossibilitiesDto.class);
        assertThat(possibleResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        movements = possibleResponse.getBody();

        assertThat(movements.getPossibilities())
                .containsExactlyInAnyOrder(
                        new PositionDto(2, 4),
                        new PositionDto(1, 3),
                        new PositionDto(0, 4),
                        new PositionDto(1, 5)
                );
    }

}
