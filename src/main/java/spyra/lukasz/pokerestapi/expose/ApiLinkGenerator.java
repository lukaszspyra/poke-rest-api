package spyra.lukasz.pokerestapi.expose;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
class ApiLinkGenerator {

    private final String apiEntryUrl;
    private final String apiSingleResource;

    public ApiLinkGenerator(@Value("${api.entry.url}") String apiEntryUrl, @Value("${api.single.resource}") String apiSingleResource) {
        this.apiEntryUrl = apiEntryUrl;
        this.apiSingleResource = apiSingleResource;
    }

    public String generateSingleResourceLink(Long id) {
        return apiSingleResource.replace("{id}", id.toString());
    }
}
