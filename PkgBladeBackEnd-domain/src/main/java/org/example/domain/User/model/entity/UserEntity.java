package org.example.domain.User.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户密码
     */
    private String userKey;
    /**
     * 用户名称
     */
    private String userName;
}
