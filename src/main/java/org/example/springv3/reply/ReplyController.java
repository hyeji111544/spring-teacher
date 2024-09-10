package org.example.springv3.reply;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.springv3.core.util.Resp;
import org.example.springv3.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class ReplyController {
    private final ReplyService replyService;
    private final HttpSession session;


    @PostMapping("/api/reply")// RequestBody 이거 받으면 json 으로 받을 수 있다.
    public ResponseEntity<?> save(@RequestBody ReplyRequest.SaveDTO saveDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        ReplyResponse.DTO replyDTO= replyService.댓글쓰기(saveDTO, sessionUser);
        System.out.println("controller : " +replyDTO.toString());
    return ResponseEntity.ok(Resp.ok(replyDTO));
    }


    @DeleteMapping("/api/reply/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        //1. 인증 체크 -> 주소로 처리 (클리어)
        //2. 서비스 호출 -> 댓글 삭제
        // session 은 오브젝트 타입으로 밖에 못가져오기 때문에 User 로 다운캐스팅 함
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.댓글삭제(id, sessionUser);

        //3. 응답
        return ResponseEntity.ok(Resp.ok(null));
    }

}
