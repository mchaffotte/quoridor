package fr.chaffotm.quoridor;

import fr.chaffotm.quoridor.model.Game;
import fr.chaffotm.quoridor.repository.GameRepository;
import fr.chaffotm.quoridor.service.GameService;
import fr.chaffotm.quoridor.controller.GameRestController;
import fr.chaffotm.quoridor.controller.ExceptionHandlingController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackageClasses = {
        GameRestController.class, GameService.class, ExceptionHandlingController.class, GameRepository.class
})
@EntityScan(basePackageClasses = {Game.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}