package org.bcliu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private Integer code;//业务状态码 0-成功 1-失败
    private String msg;//提示信息
    private T data;//响应数据

    //成功响应结果(带响应数据)
    public static <E> Result<E>success(E data){
        return new Result<>(0, "操作成功", data);
    }

    //成功响应结果
    public static Result success(){
        return new Result(0, "操作成功", null);
    }

    //失败响应
    public static Result error(String msg){
        return new Result(1, msg, null);
    }
}
