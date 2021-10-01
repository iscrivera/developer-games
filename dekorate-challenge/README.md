# Multivelopers dekorate-challenge Project

This project uses Spring Boot Framework.

If you want to learn more about Spring Boot, please visit its website: https://spring.io/projects/spring-boot .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
mvn clean install
mvn spring-boot:run
```

> **_NOTE:_**  Spring boot now running in dev mode at http://localhost:8080/.

## Packaging and deploying the application in opneshift

The application can be packaged using:
```shell script
mvn clean install
```
It produces the `openshift.yml` file in the `target\classes\META-INF\dekorate\` directory.

```shell script
oc login
oc apply -f target/classes/META-INF/dekorate/openshift.yml
oc start-build dekorate-challenge --from-dir=target/ --follow
```

## Bonus track

If you want to use Dekorate with Kubernetes or JIB uncomment the corresponding section in pom.xml and DekorateChallengeApplication.java files
pom.xml:
```shell script
		<!-- Dekorate for Kubernetes -->
<!-- 		<dependency> -->
<!-- 			<groupId>io.dekorate</groupId> -->
<!-- 			<artifactId>kubernetes-spring-starter</artifactId> -->
<!-- 			<version>0.7.5</version> -->
<!-- 		</dependency> -->

		<!-- Dekorate for Docker with JIB -->
<!-- 		<dependency> -->
<!-- 			<groupId>io.dekorate</groupId> -->
<!-- 			<artifactId>jib-annotations</artifactId> -->
<!-- 			<version>1.0.0</version> -->
<!-- 		</dependency> -->
```



DekorateChallengeApplication.java:
```shell script
//import io.dekorate.jib.annotation.JibBuild;
//import io.dekorate.kubernetes.annotation.KubernetesApplication;
import io.dekorate.kubernetes.annotation.Port;
import io.dekorate.openshift.annotation.OpenshiftApplication;

@SpringBootApplication
@OpenshiftApplication(name = "dekorate-challenge", ports = @Port(name = "web", containerPort = 8080), expose = true)
//@KubernetesApplication(name = "dekorate-challenge", ports = @Port(name = "web", containerPort = 8080), expose = true)
//@JibBuild(registry = "docker.io")
public class DekorateChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DekorateChallengeApplication.class, args);
	}

}
```


[Related guide section...](https://github.com/Red-Hat-Developer-Games/dekorate-challenge)



