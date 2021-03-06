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
        return people.stream()
                    .filter(person -> person.getId().equals(id))
                    .findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> person = selectPersonById(id);
        if (person.isEmpty()) return 0;
        people.remove(person.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person update) {
        return selectPersonById(id)
                .map( person -> {
                    int indexToDelete = people.indexOf(person);
                    if (indexToDelete >= 0) {
                        people.set(indexToDelete, new Person(id, update.getName()));
                        return 1;
                    } else return 0;
                })
                .orElse(0);
    }
}
