package com.singashi.moviecatalogueservice.resource;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.singashi.moviecatalogueservice.models.CatalogItem;
import com.singashi.moviecatalogueservice.models.Movie;
import com.singashi.moviecatalogueservice.models.Rating;
import com.singashi.moviecatalogueservice.models.UserRating;
import com.singashi.moviecatalogueservice.services.UserRatingInfo;
import com.singashi.moviecatalogueservice.services.MovieInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogueResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    UserRatingInfo userRatingInfo;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        // get all rated movie IDs
        UserRating ratings = userRatingInfo.getUserRating(userId);

        return ratings.getUserRating().stream().map(rating -> {
            return movieInfo.getCatalogItem(rating);
        }).collect(Collectors.toList());
    }

    public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId) {
        return Arrays.asList(new CatalogItem("No Movie","", 0));
    }
}
