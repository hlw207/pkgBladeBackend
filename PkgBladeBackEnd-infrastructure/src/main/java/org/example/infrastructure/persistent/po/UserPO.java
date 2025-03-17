package org.example.infrastructure.persistent.po;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * CREATE TABLE user_table (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    user_key VARCHAR(255) NOT NULL COMMENT '用户密码',
    user_name VARCHAR(255) NOT NULL COMMENT '用户名称'
) COMMENT='用户表';
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPO {
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