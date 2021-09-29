package com.microservices.moviecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableEurekaClient
public class MovieCatalogServiceApplication {
	
	@Bean
	@LoadBalanced // Load Balanced annotation is used in Service Discovery model, which indicates that does service discovery in load balanced way
	public RestTemplate getRestTemplate(){
		return new RestTemplate(); // restTemplates will be obsolete after sometime, so using the WebClient 
									// functionality listed below
	}
	
	// By writing the @LoadBalanced annotation, the rest template will get to know which URL to call, means 
	// it will start discovering services
	
	@Bean
	public WebClient.Builder getWebClientBuilder(){
		return WebClient.builder(); // create the instance of WebClient Builder, asynchronous feature!!
	}

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogServiceApplication.class, args);
	}

}
