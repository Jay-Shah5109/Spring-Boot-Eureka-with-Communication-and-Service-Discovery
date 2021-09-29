package com.microservices.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservices.moviecatalogservice.models.CatalogItem;
import com.microservices.moviecatalogservice.models.Movie;
import com.microservices.moviecatalogservice.models.Rating;
import com.microservices.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate; // use this way so that the object will be created only once
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@RequestMapping("/{userID}")
	public List<CatalogItem> getCatalog(@PathVariable("userID") String userID){
		
		UserRating ratings=restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+userID, UserRating.class);
		
		
		// Below are the steps to be performed:
		// Get all the rated movie IDs
		// For each movieID, call the movie info service and get details
		// Put all them together
		
		//RestTemplate restTemplate=new RestTemplate();
		// Traditional Method Disadvantage 2: The above line of code will create an object each time we will call
		// this service, and we dont want to do that in an actual microservices env. So we will comment out the above line
		// and use the Bean created in the lines 22 and 23
		
		/*List<Rating> ratings = Arrays.asList(new Rating("1123",4),
				new Rating("3242",5)
				);*/
		
		return ratings.getRatings().stream().map(rating -> {
		
			
		Movie movie=restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieID(), Movie.class);
		// Traditional Method Disadvantage 1: Now using the hardcoded URL in the above line is a bad code, since we dont want to have a situation
		// where the URLs will change and we will encounter a problem to replace all the codes all time, instead
		// we will require a require services to be discovered automatically.
		
			
			
			
		// Asynchronous call 	
		/*Movie movie=webClientBuilder.build()
						.get()
						.uri("http://localhost:8082/movies/"+rating.getMovieID())
						.retrieve()
						.bodyToMono(Movie.class) // Mono means that we will not get the object immediately, instead 
												// we will get it sometime ahead in future
						.block(); // The 'block' indicates that it will block the execution until Mono returns the object back 
		*/
		// The above statement is similar to creation of the rest Template in the above step on line 51, we are 
		// commenting the WebClient part above, and are proceeding with RestTemplate for further part of our
		// project.
		
		
		return new CatalogItem(movie.getName(), movie.getName(), rating.getRating());
		}).collect(Collectors.toList());
		
		//return Collections.singletonList(new CatalogItem("Chak de India", "Movie describing Indian Womens Hockey", 5));
	}

}
