package io.leoindahause.repository;

import io.leoindahause.models.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Person_rep extends CrudRepository<Person, Long> {
    
    Person findByName(String name);
    Person findByEmail(int email);
    Person findByPassword(int password);
    
}
