package spyra.lukasz.pokerestapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestOperations;
import spyra.lukasz.pokerestapi.expose.exceptions.RestTemplateResponseErrorHandler;

@SpringBootApplication
public class PokeRestApiApplication {

    private static final Logger log = LoggerFactory.getLogger(PokeRestApiApplication.class);

    public static void main(String[] args) {
        log.debug("Starting application");
        SpringApplication.run(PokeRestApiApplication.class, args);
    }


    @Bean
    public RestOperations restTemplate(RestTemplateBuilder builder) {
        log.debug("Building rest template bean with custom error handler");
        return builder.errorHandler(new RestTemplateResponseErrorHandler()).build();
    }

    @Bean
    public Long customPokeBeginIndex() {
        long beginIndex = 100000L;
        log.debug("Declaring begin index for custom pokemons as: " + beginIndex);
        return beginIndex;
    }

}
