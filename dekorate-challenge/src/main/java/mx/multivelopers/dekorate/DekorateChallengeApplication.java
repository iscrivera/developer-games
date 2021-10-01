package mx.multivelopers.dekorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
