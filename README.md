Base Spring App
====
A CRUD application to learn and implement spring concepts.

### Prerequisits
1. Install maven and add it in path
2. Download lombok and install it your eclipse

1. Spring Boot
2. IoC aka DI injection
3. [Swagger](#swagger-configuration) for Rest API documentation
4. [Spring Security](#spring-security)  
	* [Basic authentication](#basic-authentication)  
	* [Role base API authorization](role-base-api-authorization)   
5. [Spring AOP](#spring-aop)

---

## Spring Security

#### Basic Authentication
API's can be accessed by providing Base64 encoded credentials with each request, using HTTP [Authorization] header.
Also need to exclude Swagger-UI resources from security configuration in order to be accessible without credentials.

#### Protecting APIs against user roles
API's can be protected against user roles in WebSecurityConfiguration.java file.

> `http.antMatchers("/users/*").hasRole("USER")`

#### Methods level security
Method level security can be enabled using
> `@EnableGlobalMethodSecurity(prePostEnabled = true)`  

Then you can use @PreAuthorize annotaion on methods.  
> `@PreAuthorize("hasRole('ADMIN')")` or `@PreAuthorize("hasAuthority('ROLE_ADMIN')")`

---

## Spring AOP
AOP addresses the problem of cross-cutting concerns, which would be any kind of code that is repeated in different methods and 
can't normally be completely refactored into its own module, like with logging or verification. So, with AOP you can leave that 
stuff out of the main code and define it vertically.  
Mostly used for `logging` and `verification`.
[Spring docs](https://docs.spring.io/spring/docs/4.3.x/spring-framework-reference/html/aop.html)

## Other Spring Concepts
1. Validating spring properties on bootstrap and fail fast if any important property is missing.    
`BeanFactoryPostProcessor is used in VerifierBean for this purpose.`
2. StartupLoggingBean implemented to log some environment info at application startup

---

## Unit Testing

#### Automated Testing

##### Testing Getters/Setters

[OpenPojo](https://github.com/OpenPojo/openpojo) is used to test getters/setters automatically.  
See [this](https://github.com/OpenPojo/openpojo/wiki) tutorial for full capabilities of OpenPojo.

##### Testing Getters/Setters
[EqualsVerifier](http://jqno.nl/equalsverifier/) is used to test equals and hashCode methods.

---

## Swagger configuration

- Swagger UI can be seen using this URL : [/estore/swagger-ui.html](http://localhost:8181/mma/swagger-ui.html)

---

## Code Quality Analysis

#### SonarQube integration with Maven
*Prerequisites*
1. SonarQube already installed and running (url=my.sonar.host:9000)    

*Run following command to analyze project and post results on sonar server*
> `mvn clean install -Psonar sonar:sonar`

#### Code Coverage with JaCoCo maven plugin
*Run following command for code coverage and post results on sonar server*
> `mvn clean install sonar:sonar`

### Note:
#### Maven Surefire Plugin
It is used to run unit tests.

#### Maven Failsafe Plugin
It is used to run integration tests.
This plugin will not fail build during `mvn integration-test` phase.
Failsafe plugin only executes test classes named `*/IT.java, **/*IT.java, **/*ITCase.java`

1. Global exception handling
2. Object mapper

java -jar -Dspring.profiles.active=prod target/spring-boot-profile-1.0.jar