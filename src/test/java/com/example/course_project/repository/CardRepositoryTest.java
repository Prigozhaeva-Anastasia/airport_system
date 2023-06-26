package com.example.course_project.repository;

import com.example.course_project.entity.Card;
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
class CardRepositoryTest {
    @Autowired
    private CardRepository cardRepository;

    @Test
    void testGetCardByNumber() {
        Card expectedCard = new Card();
        expectedCard.setId(1l);
        expectedCard.setNumber(8745963212458756l);
        expectedCard.setMonth(11);
        expectedCard.setYear(2009);
        expectedCard.setBalance(120);
        Card card = cardRepository.getCardByNumber(8745963212458756l);
        assertEquals(expectedCard, card);
    }

    @Test
    void testGetCardsByPassengerId() {
        List<Card> cardList = cardRepository.getCardsByPassengerId(1l);
        int expectedValue = 1;
        assertEquals(expectedValue, cardList.size());
    }
}