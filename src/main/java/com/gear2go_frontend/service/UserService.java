package com.gear2go_frontend.service;

import com.gear2go_frontend.domain.AuthenticationRequest;
import com.gear2go_frontend.domain.AuthenticationResponse;
import com.gear2go_frontend.domain.Product;
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

    public void findUsersByName(String lastName, Consumer<List<User>> onSuccess, Consumer<Throwable> onError) {
        uriService.requestSecuredEndpoint(gear2GoServerProperties.getUser() + lastName, HttpMethod.GET,null, new ParameterizedTypeReference<List<User>>() {},
                onSuccess,
                onError
        );
    }

    public void updateUser(User userRequest, Consumer<List<User>> onSuccess, Consumer<Throwable> onError) {
        uriService.requestSecuredEndpoint(gear2GoServerProperties.getUser(), HttpMethod.PUT, userRequest, new ParameterizedTypeReference<List<User>>() {},
                onSuccess,
                onError
        );
    }

    public void addUser(User user) {
        URI uri = uriService.buildUri(gear2GoServerProperties.getEndpoint() + "user");
        restTemplate.postForEntity(uri, user, Void.class);
    }

    public void deleteUser(User userRequest, Consumer<List<User>> onSuccess, Consumer<Throwable> onError) {
        uriService.requestSecuredEndpoint(gear2GoServerProperties.getUser(), HttpMethod.DELETE, userRequest, new ParameterizedTypeReference<List<User>>() {},
                onSuccess,
                onError
        );
    }

    public void getAuthenticationToken(AuthenticationRequest authenticationRequest) {
        URI uri = uriService.buildUri(gear2GoServerProperties.getEndpoint() + "auth/authenticate");
        String token = restTemplate.postForObject(uri, authenticationRequest, AuthenticationResponse.class).token();
        WebStorage.setItem(WebStorage.Storage.LOCAL_STORAGE, "jwtToken", token);
    }

}
