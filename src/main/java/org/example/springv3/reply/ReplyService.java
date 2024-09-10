package org.example.springv3.reply;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springv3.board.Board;
import org.example.springv3.board.BoardRepository;
import org.example.springv3.core.error.ex.Exception403;
import org.example.springv3.core.error.ex.ExceptionApi403;
import org.example.springv3.core.error.ex.ExceptionApi404;
import org.example.springv3.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service // IoC 등록
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void 댓글삭제(int id, User sessionUser){
        Reply replyPS = replyRepository.findById(id).orElseThrow(
                ()-> new ExceptionApi404("해당 댓글을 찾을 수 없습니다.")
        );

        if(replyPS.getUser().getId() != sessionUser.getId()){
            throw new ExceptionApi403("댓글 삭제 권한이 없습니다.");
        }
        replyRepository.deleteById(id);
    }

    public ReplyResponse.DTO 댓글쓰기(ReplyRequest.SaveDTO saveDTO, User sessionUser) {
        //1 게시글 존재유무 확인
        System.out.println(saveDTO.toString());
        Board boardPS = boardRepository.findById(saveDTO.getBoardId())
                .orElseThrow(()-> new ExceptionApi404("게시글을 찾을 수 없습니다"));
        //3. 비영속 댓글 객체 만들기
        Reply reply = saveDTO.toEntity(sessionUser, boardPS);

        //2 댓글 저장 이 코드를 통해 영속 개체가 되었음 따라서 이것을 리턴해도 상관없게됨
        //
       replyRepository.save(reply);
        return new ReplyResponse.DTO(reply);
    }
}
