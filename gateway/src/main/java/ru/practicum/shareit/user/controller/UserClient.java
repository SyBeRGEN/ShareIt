package ru.practicum.shareit.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoUpdate;
import ru.practicum.shareit.web.BaseWebClient;

@Service
public class UserClient extends BaseWebClient {
    private static final String API_PREFIX = "/users";
    private static final String CACHE = "users";


    public UserClient(@Value("${shareit-server.url}") String url) {
        super(WebClient.builder()
                .baseUrl(url + API_PREFIX)
                .build());
    }

    @Cacheable(CACHE)
    public Mono<ResponseEntity<Object>> createUser(UserDto userDto) {
        return post("", userDto);
    }

    @CachePut(CACHE)
    public Mono<ResponseEntity<Object>> updateUser(UserDtoUpdate userDtoUpdate, Long userId) {
        return patch("/" + userId, userDtoUpdate);
    }

    @Cacheable(CACHE)
    public Mono<ResponseEntity<Object>> getUserById(Long userId) {
        return get("/" + userId);
    }

    @CacheEvict(CACHE)
    public Mono<ResponseEntity<Object>> deleteUser(Long userId) {
        return delete("/" + userId);
    }

    @Cacheable(CACHE)
    public Mono<ResponseEntity<Object>> getUsers() {
        return get("");
    }
}
