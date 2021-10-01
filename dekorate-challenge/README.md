# dekorate-challenge Project

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

[Related guide section...](https://github.com/Red-Hat-Developer-Games/dekorate-challenge)

