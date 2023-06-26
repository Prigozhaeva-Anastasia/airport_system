package com.example.course_project.repository;

import com.example.course_project.entity.AirTicket;
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
class AirTicketRepositoryTest {
    @Autowired
    private AirTicketRepository airTicketRepository;

    @Test
    void testGetAirTicketsByPassengerId() {
        List<AirTicket> airTicketList =airTicketRepository.getAirTicketsByPassengerId(1l);
        int expectedValue = 2;
        assertEquals(expectedValue, airTicketList.size());
    }
}