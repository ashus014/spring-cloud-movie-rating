package com.singashi.moviecatalogueservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.singashi.moviecatalogueservice.models.CatalogItem;
import com.singashi.moviecatalogueservice.models.Movie;
import com.singashi.moviecatalogueservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    public CatalogItem getCatalogItem(Rating rating) {
        // For each movie ID, call movie info service and get details
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        // Put them all together
        return new CatalogItem(movie.getName(), "Desc", rating.getRating());
    }

    public CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem("Movie name not found", "", rating.getRating());
    }

}
