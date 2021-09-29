# Spring-Boot-Eureka-with-Communication-and-Service-Discovery
The project describes the use of Service Discovery using Spring Eureka Discovery Server and Clients

<b>MICROSERVICES LEVEL 1 : COMMUNICATION AND SERVICE DISCOVERY</b>


The drawback in project till now was that it used the hardcoded URL and it was cumbersome to handle the code if the MS replicas would increase, or they are deployed on the cloud then the URLs would differ each time a new instance would spin up. So handling the configuration would be extremely difficult.

<b>Client Side Service Discovery:</b>

So service Discovery pattern is used here. It is basically a concept where the MS discover the services on their own, and communicate with each other.

We need to provide a layer of abstraction, probably it would talk with our class and behind the abstraction layer, we can keep our microservices. 

![image](https://user-images.githubusercontent.com/34195659/135326561-98894de0-745e-40e4-82de-9db3ec04f43b.png)





<b>Server Side Service Discovery:</b>

![image](https://user-images.githubusercontent.com/34195659/135326634-3a10137c-eb00-49bf-8c17-c36c8e36a2d3.png)




Note: Spring Cloud uses Client side service discovery.

Eureka: The technology that Spring Boot integrates with Netflix OSS.


We will be using Spring Cloud and Eureka, to discover the services that we are using in our code. 

![image](https://user-images.githubusercontent.com/34195659/135326658-4a1578c3-512e-4fa9-8be5-614f7cf8df7c.png)

![image](https://user-images.githubusercontent.com/34195659/135326686-fcf51e73-0b25-40ce-9b2e-7bed951ba4df.png)





Now we have created the discovery server Spring Boot Project, and we have Eureka running on port <b>8761</b>. 

![image](https://user-images.githubusercontent.com/34195659/135326717-fa36cb9d-6ca5-4582-adf3-0d89a6a59eac.png)



We cannot see instances registered with Eureka since we don’t have service discovery client installed yet on the server. So, we will create another Spring Boot project now.

Adding below lines in application.properties file will tell the Eureka Server that it is the only server present and it does not need to search for any Eureka Clients, since we have one and only one Eureka Server in our project.<br><br>
```

server.port=8761
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false

```
<br>
After adding the dependencies in the pom.xml for the movie-info service, we can see that when both the service discovery and the movie info services are up, the eureka client instance is registered with the eureka discovery server.


Dependencies added:

```

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.3.12.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.microservices</groupId>
	<artifactId>movie-info-service</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>movie-info-service</name>
	<description>Spring Boot Project for movie info service</description>
	<properties>
		<java.version>1.8</java.version>
		<spring-cloud.version>Greenwich.RELEASE</spring-cloud.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-eureka-client -->
		<dependency>
		    <groupId>org.springframework.cloud</groupId>
		    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		    <version>3.0.1</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>
	
	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
	
	<repositories>
		<repository>
			<id>spring-milestones</id>
			<name>Spring Milestones</name>
			<url>https://repo.spring.io/milestone</url>
		</repository>
	</repositories>

</project>

```
NOTE:

@LoadBalanced - This annotation will state that the service is ready to act as Eureka Client

```

@SpringBootApplication
@EnableEurekaClient
public class MovieCatalogServiceApplication {
	
	@Bean
	@LoadBalanced // Load Balanced annotation is used in Service Discovery model, which indicates that does service discovery in load balanced way
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
		.
		.
		.

```

We can see that UNKNOWN instance is registered below -

![image](https://user-images.githubusercontent.com/34195659/135332331-32e153da-1b4c-406b-ac9c-fa3575cb5bfc.png)




We added the below property to application.properties file and now we can see that the instance has registered with a specific name -<b> movie info service</b>

![image](https://user-images.githubusercontent.com/34195659/135332385-541b4d73-fbd5-4e61-a877-ac5925b35fd6.png)





After running the discovery server and subsequent 3 microservices, we can see that all the services have registered themselves on the discovery server.

![image](https://user-images.githubusercontent.com/34195659/135332424-40a33281-a269-4a3a-ad9e-c52d83d13e38.png)

