package com.redhat.developergames;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.redhat.developergames.model.Weather;
import com.redhat.developergames.repository.WeatherRepository;

import io.dekorate.kubernetes.annotation.Port;
import io.dekorate.openshift.annotation.OpenshiftApplication;

@SpringBootApplication
@EnableCaching
@OpenshiftApplication(name = "spring-boot-datagrid-challenge", ports = @Port(name = "web", containerPort = 8080), expose = true)
@RestController
public class WeatherApp {

	@Autowired
	WeatherRepository weatherRepository;

	private final RemoteCacheManager cacheManager;

	@Autowired
	public WeatherApp(RemoteCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot with Multivelopers!";
	}

	@GetMapping("/weather/{location}")
	@Cacheable("weather")
	public Object getByLocation(@PathVariable String location) {
		Weather weather = weatherRepository.getByLocation(location);
		if (weather == null) {
			return String.format("Weather for location %s not found", location);
		}

		if (null != cacheManager.getCache("sessions")) {
			cacheManager.getCache("sessions").put("latest", weather);
		}
		return weather;
	}

	@GetMapping("/latest")
	public Object latestLocation() {
		if (null != cacheManager.getCache("sessions") && null != cacheManager.getCache("sessions").get("latest")) {
			return cacheManager.getCache("sessions").get("latest");
		}
		return "ops, I did it again!";
	}

	public static void main(String... args) {
		new SpringApplicationBuilder().sources(WeatherApp.class).run(args);
	}
}
