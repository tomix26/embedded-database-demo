package com.github.tomix26.embedded.database.demo.domain;

import com.github.tomix26.embedded.database.demo.annotation.ReadOnlyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

@ReadOnlyRepository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
