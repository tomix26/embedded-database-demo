package com.github.tomix26.embedded.database.demo;

import com.github.tomix26.embedded.database.demo.annotation.PostgresDataJpaTest;
import com.github.tomix26.embedded.database.demo.domain.Person;
import com.github.tomix26.embedded.database.demo.domain.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@PostgresDataJpaTest // custom composite annotation
public class CustomDataJpaAnnotationTest {

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
