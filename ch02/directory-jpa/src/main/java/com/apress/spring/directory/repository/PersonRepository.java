package com.apress.spring.directory.repository;

import com.apress.spring.directory.domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person,String> {
}
