package com.github.tomix26.embedded.database.demo;

import com.github.tomix26.embedded.database.demo.annotation.PostgresDataJpaTest;
import com.github.tomix26.embedded.database.demo.domain.Person;
import com.github.tomix26.embedded.database.demo.domain.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@PostgresDataJpaTest // custom composite annotation
public class CustomDataJpaAnnotationTest {

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
