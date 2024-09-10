package org.example.springv3.core.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Resp<T> {
    private Integer status;
    private String msg;
    private T body;

    public static <B> Resp<?> ok(B body){
        return new Resp<>(200, "성공", body); // 돌랴줄 ㅐ이ㅑ
    }

    public static Resp<?> fail(Integer status, String msg){
        return new Resp<>(status, msg, null); // 실패해서 바디에 넣을 게 엇음
    }
}
