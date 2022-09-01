package br.com.ecore.common;

import br.com.ecore.exception.EcoreException;
import br.com.ecore.exception.EcoreInternalServerException;
import br.com.ecore.team.json.Team;
import br.com.ecore.user.json.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
@PropertySource("classpath:properties/app.properties")
public class ExistingApiService {

    private static final Logger LOGGER = LogManager.getLogger(ExistingApiService.class);

    @Value("${existing.rest.endpoint.host:https://cgjresszgg.execute-api.eu-west-1.amazonaws.com}")
    private String serviceUrl;

    public Optional<List<Team>> getTeams() throws EcoreException {
        try {
            RestTemplate rest = new RestTemplate();
            ResponseEntity<List<Team>> exchange = rest.exchange(
                    getUrl(serviceUrl, "/teams"), HttpMethod.GET, null, new ParameterizedTypeReference<List<Team>>() {
                    });
            isValidResponse(exchange);
            return Optional.ofNullable(exchange.getBody());
        } catch (RestClientException e) {
            LOGGER.error("Get all teams request failed to be executed", e);
            return Optional.empty();
        }
    }

    public Optional<Team> getTeamByTeamId(String id) throws EcoreException {
        try {
            RestTemplate rest = new RestTemplate();
            ResponseEntity<Team> exchange = rest.exchange(
                    getUrl(serviceUrl, "/teams/" + id), HttpMethod.GET, null, new ParameterizedTypeReference<Team>() {
                    });
            isValidResponse(exchange);
            return Optional.ofNullable(exchange.getBody());
        } catch (RestClientException e) {
            LOGGER.error("Get team request failed to be executed", e);
            return Optional.empty();
        }
    }

    public Optional<List<User>> getAllUsers() throws EcoreException {
        try {
            RestTemplate rest = new RestTemplate();
            ResponseEntity<List<User>> exchange = rest.exchange(
                    getUrl(serviceUrl, "/users"), HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                    });
            isValidResponse(exchange);
            return Optional.ofNullable(exchange.getBody());
        } catch (RestClientException e) {
            LOGGER.error("Get all users request failed to be executed", e);
            return Optional.empty();
        }
    }

    public Optional<User> getUserByUserId(String userId) throws EcoreException {
        try {
            RestTemplate rest = new RestTemplate();
            ResponseEntity<User> exchange = rest.exchange(
                    getUrl(serviceUrl, "/users/" + userId), HttpMethod.GET, null, new ParameterizedTypeReference<User>() {
                    });
            isValidResponse(exchange);
            return Optional.ofNullable(exchange.getBody());
        } catch (RestClientException e) {
            LOGGER.error("Get user by user id request failed to be executed", e);
            return Optional.empty();
        }
    }

    protected URI getUrl(String url, String suffix) throws EcoreException {
        return getUrl(url + suffix);
    }

    protected URI getUrl(String url) throws EcoreException {
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            LOGGER.error(format("URL could not be generate with provided url: {0}", url), e);
            throw new EcoreInternalServerException("Error executing request");
        }
    }

    protected void isValidResponse(ResponseEntity<?> response) throws EcoreInternalServerException {
        if(response != null && response.getStatusCode().equals(HttpStatus.OK)) {
            return;
        }

        throw new EcoreInternalServerException("Response is null or response status: {0} expected was not found", HttpStatus.OK);
    }

}
