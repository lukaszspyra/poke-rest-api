package spyra.lukasz.pokerestapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestOperations;

@SpringBootApplication
public class PokeRestApiApplication {

    private static final Logger log = LoggerFactory.getLogger(PokeRestApiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PokeRestApiApplication.class, args);
    }


    @Bean
    public RestOperations restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public Long customPokeBeginIndex() {
        return 100000L;
    }

}
