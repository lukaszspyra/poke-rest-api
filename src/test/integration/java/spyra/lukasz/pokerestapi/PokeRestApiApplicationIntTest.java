package spyra.lukasz.pokerestapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spyra.lukasz.pokerestapi.read.PokeReadController;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PokeRestApiApplicationIntTest {

    @Autowired
    private PokeReadController controller;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(controller);
    }

}
