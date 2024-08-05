package io.leoindahause.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import io.leoindahause.model.Person;

@Repository
public interface Person_rep extends CrudRepository<Person, Integer> {
    
}
