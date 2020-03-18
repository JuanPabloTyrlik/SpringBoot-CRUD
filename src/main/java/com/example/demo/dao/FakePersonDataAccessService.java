package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDAO")
public class FakePersonDataAccessService implements IPersonDAO {

    private static List<Person> people = new ArrayList<>();

    @Override
    public int insertPerson(UUID uuid, Person person) {
        people.add(new Person(uuid,person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAll() {
        return people;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return Optional.empty();
    }

    @Override
    public int deletePersonById(UUID id) {
        return 0;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        return 0;
    }
}
