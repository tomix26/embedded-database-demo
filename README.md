# Introduction
It is a Spring Boot application demonstrating the use of the [embedded-database-spring-test](https://github.com/zonkyio/embedded-database-spring-test) library.

## Maven Configuration
Add the following maven dependency:
```xml
<dependency>
    <groupId>io.zonky.test</groupId>
    <artifactId>embedded-database-spring-test</artifactId>
    <version>2.0.1</version>
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
Further, make sure that you do not use `org.flywaydb.test.junit.FlywayTestExecutionListener`. Because the library has its own test execution listener that can optimize database initialization and this optimization has no effect if the `FlywayTestExecutionListener` is applied.

# Example
An example of test class demonstrating the use of the embedded database.
```java
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase
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
