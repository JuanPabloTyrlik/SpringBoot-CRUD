package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("H2")
public class PersonDataAccessService implements IPersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        String sql = "INSERT INTO Person (id, name) VALUES (?, ?)";
        return jdbcTemplate.update(sql, id, person.getName());
    }

    @Override
    public List<Person> selectAll() {
        final String sql = "SELECT * FROM Person";
        List<Person> people = jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Person(id, name);
        });
        return people;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        final String sql = "SELECT * FROM Person WHERE id = ?";
        Person person = jdbcTemplate.queryForObject(sql,
                new Object[]{id},
                (resultSet, i) -> {
                    UUID PersonId = UUID.fromString(resultSet.getString("id"));
                    String name = resultSet.getString("name");
                    return new Person(PersonId, name);
                });
        return Optional.ofNullable(person);
    }

    @Override
    public int deletePersonById(UUID id) {
        String sql = "DELETE FROM Person WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        String sql = "UPDATE Person SET Name = ? WHERE id = ?";
        return jdbcTemplate.update(sql, person.getName(), id);
    }
}
