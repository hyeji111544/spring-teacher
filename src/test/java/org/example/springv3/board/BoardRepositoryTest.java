package org.example.springv3.board;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest // JpaTest 를 상속하면 import 할 필요가 X
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void mFindAll_test() throws JsonProcessingException {
        // given
        String title = "제목";
        // when
        Pageable pageable = PageRequest.of(0, 3);
        Page<Board> boardPG = boardRepository.mFindAll(title,pageable);

        // eye
        ObjectMapper om = new ObjectMapper(); // 메시지 컨버터, JSON -> Java, Java -> JSON
        String responseBody = om.writeValueAsString(boardPG); // Java 객체 boardPG를 JSON 문자열로 직렬화
        //Board board = om.readValue(responseBody, Board.class); // JSON 문자열을 다시 Board 클래스 객체로 역직렬화

        System.out.println(responseBody);
        /*
         @JsonIgnore 보드엔티티 유저, 리플리에 추가해야 돌아감
         */
    }

    @Test
    public void mFindAllV2_test() throws JsonProcessingException {
        // given
        String title = "제목";

        // when
        Pageable pageable = PageRequest.of(0, 3);
        Page<Board> boardPG = boardRepository.mFindAll(title, pageable);

        // eye
        System.out.println(boardPG.getContent());
    }

    @Test
    public void mFindByIdWithReply_test(){
        Board board = boardRepository.mFindByIdWithReply(5).get();
        System.out.println(board.getReplies().get(0).getComment());
    }

}
