package spyra.lukasz.pokerestapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestOperations;
import spyra.lukasz.pokerestapi.read.exceptions.RestTemplateResponseErrorHandler;

@EnableCaching
@SpringBootApplication
public class PokeRestApiApplication {

    private static final Logger log = LoggerFactory.getLogger(PokeRestApiApplication.class);

    public static void main(String[] args) {
        log.debug("Starting application");
        SpringApplication.run(PokeRestApiApplication.class, args);
    }

    /**
     * Adds custom error handler to {@link org.springframework.web.client.RestTemplate}
     * @see RestTemplateResponseErrorHandler
     * @param builder
     * @return
     */
    @Bean
    public RestOperations restTemplate(RestTemplateBuilder builder) {
        log.debug("Building rest template bean with custom error handler");
        return builder.errorHandler(new RestTemplateResponseErrorHandler()).build();
    }

    /**
     * Defines starting index for custom pokemons created by users
     * @return custom index
     */
    @Bean
    public Long customPokeBeginIndex() {
        long beginIndex = 900000L;
        log.debug("Declaring begin index for custom pokemons as: " + beginIndex);
        return beginIndex;
    }

}
