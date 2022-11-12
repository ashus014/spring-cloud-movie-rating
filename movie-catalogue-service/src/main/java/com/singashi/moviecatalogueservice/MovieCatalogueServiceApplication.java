package com.singashi.moviecatalogueservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class MovieCatalogueServiceApplication {

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
//		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		// setting timeout for 3 seconds
//		clientHttpRequestFactory.setConnectTimeout(3000);
		// returning RestTemplate Object with 3 seconds timeout
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogueServiceApplication.class, args);
	}

}
