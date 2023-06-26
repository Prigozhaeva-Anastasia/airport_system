package com.example.course_project.repository;

import com.example.course_project.entity.DirectFlight;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"file:src/test/resources/db/clear_all.sql","file:src/test/resources/db/insert_script.sql"})
class DirectFlightRepositoryTest {
    @Autowired
    private DirectFlightRepository directFlightRepository;

    @Test
    void testGetDirectFlightByFlightNumber() {
        DirectFlight directFlight = directFlightRepository.getDirectFlightByFlightNumber("AS123");
        assertNotNull(directFlight);
    }

    @Test
    void testGetDirectFlightsByDirection() {
        List<DirectFlight> directFlightList = directFlightRepository.getDirectFlightsByDirection("Moscow", "Stambul");
        int expectedValue = 1;
        assertEquals(expectedValue, directFlightList.size());
    }
}