package com.example.course_project.repository;

import com.example.course_project.entity.Review;
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
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;
    @Test
    void testGetReviewsByAirlineId() {
        List<Review> reviewList = reviewRepository.getReviewsByAirlineId(1l);
        int expectedValue = 1;
        assertEquals(expectedValue, reviewList.size());
    }
}