# Introduction
It is a Spring Boot application demonstrating the use of [embedded-database-spring-test](https://github.com/zonkyio/embedded-database-spring-test) library.

# Important Configuration Steps
There are some steps that are required for proper function of the embedded database together with Spring Boot framework.

## Maven Configuration
Because Spring Boot uses older version of `flyway-core` dependency it is necessary to add the following section to the parent `pom.xml` file:
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-core</artifactId>
            <version>4.2.0</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```
## Flyway Configuration
It is also necessary to add the following property to your application configuration:
```properties
flyway.schemas=xxx
```
Finally, make sure that you do not use `org.flywaydb.test.junit.FlywayTestExecutionListener`. Because it must be replaced by `io.zonky.test.db.flyway.OptimizedFlywayTestExecutionListener`, which is applied automatically.

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
		Person person = personRepository.findOne(1L);

		assertThat(person).isNotNull();
		assertThat(person.getId()).isNotNull();
		assertThat(person.getFirstName()).isEqualTo("Dave");
		assertThat(person.getLastName()).isEqualTo("Syer");
	}
}
```
