package gateway;

import reactor.core.publisher.Mono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Imports from newer project (hoxton build)
//import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

// tag::code[]
@SpringBootApplication
@EnableConfigurationProperties(UriConfiguration.class)
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	//@Bean
	//public ServerCodecConfigurer serverCodecConfigurer() {
	//   return ServerCodecConfigurer.create();
	//}

	// tag::route-locator[]
	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
		String httpUri = uriConfiguration.getHttpbin();
		//String httpDockerUri = uriConfiguration.getDocker();
		return builder.routes()
				.route(p -> p.path("/get").filters(f -> f.addRequestHeader("Hello", "World")).uri(httpUri))
				//.route(p -> p.path("/metrics").filters(f -> f.addRequestHeader("Hello", "World")).uri(httpDockerUri))
				//.route(p -> p.path("/headers").filters(f -> f.addRequestHeader("Hello", "World")).uri(httpUri))
				//.route(p -> p.path("/ip").filters(f -> f.addRequestHeader("Hello", "World")).uri(httpUri))
				//.route(p -> p.path("/user-agent").filters(f -> f.addRequestHeader("Hello", "World")).uri(httpUri))
				//.route(p -> p.path("/anything").filters(f -> f.addRequestHeader("Hello", "World")).uri(httpUri))
				//.route(p -> p.path("/uuid").filters(f -> f.addRequestHeader("Hello", "World")).uri(httpUri))
				//.route(p -> p.path("/stream/*").filters(f -> f.addRequestHeader("Hello", "World")).uri(httpUri))				
				//.route(p -> p.path("/github").filters(f -> f.rewritePath("/github", "/")).uri("https://github.com"))
				//.route(p -> p.path("/dzone").filters(f -> f.rewritePath("/dzone", "/")).uri("https://dzone.com"))
				//.route(p -> p.path("/garagesale").filters(f -> f.rewritePath("/garagesale", "/")).uri("http://host.docker.internal:8080"))
				//.route(p -> p.host("*.hystrix.com")
				//		.filters(f -> f.hystrix(config -> config.setName("mycmd").setFallbackUri("forward:/fallback")))
				//		.uri(httpUri))
				//.route(p -> p.path("/NettyRoutingFilter").filters(f -> f.)  // Add NettyRoutingFilter here to test!
				.build();
	}
	// end::route-locator[]

	// tag::fallback[]
	@RequestMapping("/fallback")
	public Mono<String> fallback() {
		return Mono.just("fallback");
	}
	// end::fallback[]
}

// tag::uri-configuration[]
@ConfigurationProperties
class UriConfiguration {

	//private String httpbin = "http://httpbin.org:80";
	private String httpbin = "http://host.docker.internal:10000";
	private String docker = "http://host.docker.internal:9323";

	public String getHttpbin() {
		return httpbin;
	}
	
	public String getDocker() {
		return docker;
	}

	public void setHttpbin(String httpbin) {
		this.httpbin = httpbin;
	}
	
	public void setDocker(String docker) {
		this.docker = docker;
	}
}
// end::uri-configuration[]
// end::code[]
