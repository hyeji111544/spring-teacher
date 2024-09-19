package org.example.springv3.board;

import lombok.Data;
import org.example.springv3.reply.Reply;
import org.example.springv3.user.User;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class BoardResponse {

    @Data
    public static class ListDTO {
        private Integer id;
        private String title;
        private Long count;

        public ListDTO(Integer id, String title, Long count) {
            this.id = id;
            this.title = title;
            this.count = count;
        }
    }

    @Data
    public static class PageDTO {
        private Integer number; // 현재페이지
        private Integer totalPages; // 전체페이지 개수
        private Integer size; // 한페이지에 아이템 개수
        private Boolean first;
        private Boolean last;
        private Integer prev; // 현재페이지 -1
        private Integer next; // 현재페이지 +1
        private String keyword; // 검색 제목
        // 번호 저장할 곳
        /*
        num 0 = 0(0,1,2)
        num 1 = 1(0,1,2)
        num 2 = 2(0,1,2)
        num 3 = 3(3,4,5)
        num 4 = 4(3,4,5)
        num 5 = 5(3,4,5)
         */
        private List<Integer> numbers= new ArrayList<>();
        // 리스트들
        private List<Content> content = new ArrayList<>();

        public PageDTO(Page<Board> boardList, String title) {
            this.number = boardList.getNumber();
            this.totalPages = boardList.getTotalPages();
            this.size = boardList.getSize();
            this.first = boardList.isFirst();
            this.last = boardList.isLast();
            this.prev = (number > 0) ? number - 1 : number;
            this.next = (!last) ? number + 1 : number;
            this.keyword = title;

            int temp = (number / 3)*3; // 0 -> 0, 3 -> 3, 6 -> 6

            for(int i=temp; i<temp+3; i++){ // 0
                this.numbers.add(i);
            }

            for(Board board : boardList){
                content.add(new Content(board));
            }
        }

        @Data
        class Content {
            private Integer id;
            private String title;

            public Content(Board board){
                this.id = board.getId();
                this.title = board.getTitle();
            }
        }
    }

    @Data
    public static class DTO {
        private Integer id;
        private String title;
        private String content;

        public DTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
        }
    }

    @Data
    public static class DetailDTO {

        private Integer id;
        private String title;
        private String content;
        private Boolean isOwner;
        private String username;

        // 댓글들
        private List<ReplyDTO> replies = new ArrayList<>(); //엔티티 말고 DTO 를 넣어야함. 엔티티 넣으면 레이지로딩? 나옴


        public DetailDTO(Board board, User sessionUser) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.isOwner = false;

            if (sessionUser != null) {
                if (board.getUser().getId() == sessionUser.getId()) {
                    isOwner = true; // 권한체크

                }
            }
            this.username = board.getUser().getUsername();

            for (Reply reply : board.getReplies()) {
                replies.add(new ReplyDTO(reply, sessionUser));
            }
        }

        @Data
        class ReplyDTO {
            private Integer id;
            private String comment;
            private String username;
            private Boolean isOwner;

            public ReplyDTO(Reply reply, User sessionUser) {
                this.id = reply.getId();
                this.comment = reply.getComment();
                this.username = reply.getUser().getUsername();
                this.isOwner = false;

                if (sessionUser != null) {
                    if (reply.getUser().getId() == sessionUser.getId()) {
                        isOwner = true; // 권한체크
                    }
                }
            }
        }
    }
}
