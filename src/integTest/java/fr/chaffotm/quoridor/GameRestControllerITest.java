package fr.chaffotm.quoridor;

import fr.chaffotm.quoridor.controller.error.BadRequestBody;
import fr.chaffotm.quoridor.controller.error.ErrorBody;
import fr.chaffotm.quoridor.dto.GameDto;
import fr.chaffotm.quoridor.interceptor.JsonInterceptor;
import fr.chaffotm.quoridor.interceptor.LoggingInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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

}
