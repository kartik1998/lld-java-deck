## Spring Boot Reference

### Index

| Index                | Reference                                                                                                    |
| -------------------- | ------------------------------------------------------------------------------------------------------------ |
| `tutorial`           | [youtube link](https://www.youtube.com/watch?v=zvR-Oif_nxg&t=8858s)                                          |
| `h2 branch`          | [springboot-deck/h2](https://github.com/kartik1998/springboot-deck/tree/h2)                                  |
| `To view h2 console` | `http://localhost:5000/h2-console`                                                                           |
| `jpa docs`           | [link](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation) |
| `jpa branch`         | [springboot-deck/jpa](https://github.com/kartik1998/springboot-deck/tree/jpa)                                                                                                          |

### API Reference :

[![Run in Postman](https://run.pstmn.io/button.svg)](https://www.getpostman.com/collections/2635ba64c44431b429bb)

### How to create a jar file:

1. Set the version in pom.xml
2. Run: `mvn clean install` (this command will run all the tests and will build the jar)
3. After the build is successfull you can find the jar in the target folder as `<project-name>-<version>.jar`
4. To trigger the jar use `java -jar target/<file.jar> --spring.profiles.active=<profile>`

### Monitoring Application Metrics with SpringBoot Actuator

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

- To view the metrics you can visit: `http://{url}/actuator`
