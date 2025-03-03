package org.example.domain.User.service;

public interface IUserService {
    /**
     *
     * @param username: 用户名
     * @return 用户ID
     */
    Long getUserIdByName(String username);
}
