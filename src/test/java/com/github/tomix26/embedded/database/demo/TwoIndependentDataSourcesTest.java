package com.github.tomix26.embedded.database.demo;

import com.github.tomix26.embedded.database.demo.domain.Person;
import com.github.tomix26.embedded.database.demo.domain.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayConfigurationCustomizer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.Optional;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureEmbeddedDatabase(beanName = "primaryDataSource")
@AutoConfigureEmbeddedDatabase(beanName = "readOnlyDataSource")
public class TwoIndependentDataSourcesTest {

	@TestConfiguration
	static class Config {

		@Bean
		public FlywayConfigurationCustomizer flywayConfigurationCustomizer(
				@Qualifier("readOnlyDataSource") DataSource readOnlyDataSource) {
			return configuration -> configuration.dataSource(readOnlyDataSource);
		}
	}

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
