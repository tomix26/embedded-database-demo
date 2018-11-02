# Introduction
It is a Spring Boot application demonstrating the use of [embedded-database-spring-test](https://github.com/zonkyio/embedded-database-spring-test) library.

## Maven Configuration
Add the following maven dependency:
```xml
<dependency>
    <groupId>io.zonky.test</groupId>
    <artifactId>embedded-database-spring-test</artifactId>
    <version>1.3.3</version>
    <scope>test</scope>
</dependency>
```
## Flyway Configuration
Add the following property to your application configuration:
```properties
# Sets the schemas managed by Flyway -> change the xxx value to the name of your schema
# flyway.schemas=xxx // for spring boot 1.x.x
spring.flyway.schemas=xxx // for spring boot 2.x.x
```
Further, make sure that you do not use `org.flywaydb.test.junit.FlywayTestExecutionListener`. Because the library has its own test execution listener that can optimize database initialization. But this optimization has no effect if the `FlywayTestExecutionListener` is applied.
## Spring Boot 2 Configuration
Since Spring Boot 2, there is a compatibility issue with Hibernate and Postgres Driver. Add the following property to your application configuration to fix that:
```properties
# Workaround for a compatibility issue of Spring Boot 2 with Hibernate and Postgres Driver
# See https://github.com/spring-projects/spring-boot/issues/12007
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
```

# Example
An example of test class demonstrating the use of the embedded database.
```java
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = NONE)
@AutoConfigureEmbeddedDatabase
@DataJpaTest
public class SpringDataJpaAnnotationTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testEmbeddedDatabase() {
        Optional<Person> personOptional = personRepository.findById(1L);

        assertThat(personOptional).hasValueSatisfying(person -> {
            assertThat(person.getId()).isNotNull();
            assertThat(person.getFirstName()).isEqualTo("Dave");
            assertThat(person.getLastName()).isEqualTo("Syer");
        });
    }
}
```
