package org.example.infrastructure.persistent.po;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPO {
    /**
     * 用户ID
     */
    private long userId;
    /**
     * 用户密码
     */
    private String userKey;
    /**
     * 用户名称
     */
    private String userName;

}