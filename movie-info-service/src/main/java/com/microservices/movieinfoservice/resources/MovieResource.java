package com.microservices.movieinfoservice.resources;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.movieinfoservice.models.Movie;

@RestController
@RequestMapping("/movies")
public class MovieResource {
	
	
	@RequestMapping("/{movieID}")
	public Movie getMovie(@PathVariable("movieID") String movie){
		return new Movie("Transformers","Hollywood Movie");
	}

}
