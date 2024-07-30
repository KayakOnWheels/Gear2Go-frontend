package com.gear2go_frontend.service;

import com.gear2go_frontend.domain.AuthenticationRequest;
import com.gear2go_frontend.domain.AuthenticationResponse;
import com.gear2go_frontend.domain.User;
import com.gear2go_frontend.properties.Gear2GoServerProperties;
import com.vaadin.flow.component.page.WebStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;


@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;
    private final UriService uriService;
    private final Gear2GoServerProperties gear2GoServerProperties;


    public void getUserList(Consumer<List<User>> onSuccess, Consumer<Throwable> onError) {
        uriService.requestSecuredEndpoint(gear2GoServerProperties.getUser(), HttpMethod.GET,null, new ParameterizedTypeReference<List<User>>() {},
                onSuccess,
                onError
        );
    }


    public List<User> findUsersByName(String lastName) {
        URI uri = uriService.buildUri(gear2GoServerProperties.getEndpoint() + "user/" + lastName);
        User[] userArray = restTemplate.getForObject(uri, User[].class);

        return userArray != null ? Arrays.asList(userArray) : Collections.emptyList();
    }

    public void updateUser(User user) {
        URI uri = uriService.buildUri(gear2GoServerProperties.getEndpoint() + "user");
        restTemplate.put(uri, user);
    }

    public void addUser(User user) {
        URI uri = uriService.buildUri(gear2GoServerProperties.getEndpoint() + "user");
        restTemplate.postForEntity(uri, user, Void.class);
    }

    public void deleteUser(Long id) {
        URI uri = uriService.buildUri(gear2GoServerProperties.getEndpoint() + "user/" + id);
        restTemplate.delete(uri);
    }

    public void getAuthenticationToken(AuthenticationRequest authenticationRequest) {
        URI uri = uriService.buildUri(gear2GoServerProperties.getEndpoint() + "auth/authenticate");
        String token = restTemplate.postForObject(uri, authenticationRequest, AuthenticationResponse.class).token();
        WebStorage.setItem(WebStorage.Storage.LOCAL_STORAGE, "jwtToken", token);
    }

}
