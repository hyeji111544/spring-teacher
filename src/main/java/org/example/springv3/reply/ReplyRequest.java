package org.example.springv3.reply;

import lombok.Data;
import org.example.springv3.board.Board;
import org.example.springv3.user.User;

public class ReplyRequest {

    @Data
    public static class SaveDTO {
        private Integer boardId;
        private String comment;

        // insert into reply_tb(comment, board_id, user_id, created_at) values('댓글1', 5, 1, now());
        // 바로 boardId 받아올 수 없음 빌더의 board 는 orm 이라서
        public Reply toEntity(User sessionUser, Board board) {
            return Reply.builder()
                    .comment(comment)
                    .user(sessionUser)
                    .board(board)
                    .build();
        }
    }
}
