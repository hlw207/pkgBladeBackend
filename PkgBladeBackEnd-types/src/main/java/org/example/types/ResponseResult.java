package org.example.types;

import lombok.Getter;
import org.example.types.enums.ResponseCode;

@Getter
public class ResponseResult<T> {
    private final ResponseCode responseCode;
    private final T data;
    private String exception = "";


    // 使用方法：ResponseResult<String> result = ResponseCode.SUCCESS.withData("操作成功");
    public ResponseResult(ResponseCode responseCode, T data) {
        this.responseCode = responseCode;
        this.data = data;
    }

    public ResponseResult(ResponseCode responseCode, T data, String exception) {
        this.responseCode = responseCode;
        this.data = data;
        this.exception = exception;
    }

}