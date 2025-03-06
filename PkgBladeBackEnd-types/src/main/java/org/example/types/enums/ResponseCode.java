package org.example.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.types.ResponseResult;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS("0000", "成功"),
    UN_ERROR("0001", "未知失败"),
    ILLEGAL_PARAMETER("0002", "非法参数"),
    ;

    private String code;
    private String info;

    // 定义一个方法，接受泛型参数 data
    public <T> ResponseResult<T> withData(T data) {
        return new ResponseResult<>(this, data);
    }

    public <T> ResponseResult<T> withException(String exception) {
        return new ResponseResult<>(this, null, exception);
    }

}
