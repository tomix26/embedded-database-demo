# Introduction
It is a Spring Boot application demonstrating the use of [embedded-database-spring-test](https://github.com/zonkyio/embedded-database-spring-test) library.

## Maven Configuration
Add maven dependency:
```xml
<dependency>
    <groupId>io.zonky.test</groupId>
    <artifactId>embedded-database-spring-test</artifactId>
    <version>1.3.0</version>
    <scope>test</scope>
</dependency>
```
## Flyway Configuration
It is recommended to add the following property to your application configuration:
```properties
# flyway.schemas=xxx // for spring boot 1.x.x
spring.flyway.schemas=xxx // change the value to the name of your schema
```
Further, make sure that you do not use `org.flywaydb.test.junit.FlywayTestExecutionListener`. Because the library has its own listener and there would be a conflict.

# Example
An example of test class demonstrating the use of the embedded database.
```java
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = NONE)
@AutoConfigureEmbeddedDatabase
@FlywayTest
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
