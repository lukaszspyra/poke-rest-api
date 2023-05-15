package spyra.lukasz.pokerestapi.expose.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

/**
 * Handles error codes received during parsing jsons from {@link org.springframework.web.client.RestTemplate#getForObject(String, Class, Object...)}
 */
@Component
public class RestTemplateResponseErrorHandler
        implements ResponseErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(RestTemplateResponseErrorHandler.class);


    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return (httpResponse.getStatusCode().series() == CLIENT_ERROR
                || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    /**
     * In case of client error with status code {@link HttpStatus#NOT_FOUND} forwards it to the rest response
     * @param httpResponse the response with the error
     * @throws IOException
     */
    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        HttpStatus statusCode = httpResponse.getStatusCode();
        log.debug("Returned poke api error with status code: " + statusCode);
        if (statusCode.series() == HttpStatus.Series.CLIENT_ERROR) {
            if (statusCode == HttpStatus.NOT_FOUND) {
                log.debug("Rethrowing ResponseStatusException with status NOT_FOUND");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
            }
        }
    }
}
