package com.fabri.malisssimoBot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

@Service
public class JokeService {

    private HttpEntity request;
    private HttpHeaders headers;
    public static final String URL_JOKE_SERVICE = "http://localhost:8081/retrieveJoke";

    @Autowired
    private RestTemplate restTemplate;

    public JokeService() {
        this.headers = new HttpHeaders();
        this.request = new HttpEntity(this.headers);
    }

    public String retrieveRandomJoke() {
        ResponseEntity<String> response = restTemplate.exchange(URL_JOKE_SERVICE, HttpMethod.POST, this.request, String.class);
        return response.getBody();
    }

    public String retrieveLaugh() {
        Random rand = new Random();
        List<String> listOfLaugh = new ArrayList<>(asList(
                "\uD83E\uDD23 Es muy bueno!!",
                "\uD83E\uDD23 \uD83E\uDD23",
                "\uD83D\uDE02 Sabía que iba a gustarte!",
                "\uD83D\uDE05",
                "\uD83D\uDE04 jajaj",
                "\uD83E\uDD23 jajaja!!",
                "jaja!!"
        ));
        return listOfLaugh.get(rand.nextInt(listOfLaugh.size()));
    }
}
