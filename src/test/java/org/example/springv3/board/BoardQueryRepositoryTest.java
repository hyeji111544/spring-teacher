package org.example.springv3.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Import(BoardQueryRepository.class) //JPA Repository 가 아니어서, import 필요
@DataJpaTest
class BoardQueryRepositoryTest {

    @Autowired
    private BoardQueryRepository boardQueryRepository;

    @Test
    void selectV1_test() {
        //given
        //when
        List<BoardResponse.ListDTO> boardList= boardQueryRepository.selectV1();
        //eye
        System.out.println("-----------------------------------------"+boardList);
    }
}