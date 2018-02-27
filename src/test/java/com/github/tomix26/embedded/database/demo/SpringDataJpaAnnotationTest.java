package com.github.tomix26.embedded.database.demo;

import com.github.tomix26.embedded.database.demo.domain.Person;
import com.github.tomix26.embedded.database.demo.domain.PersonRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

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
