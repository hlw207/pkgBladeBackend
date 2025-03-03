package org.example.domain.User.service;


public interface ILoginService {

    /**
     *
     * @param username: 用户名
     * @param password: 要验证的密码
     * @return 密码是否符合
     */
    public boolean login(String username, String password);

    /**
     *
     * @param username: 用户名
     * @param password: 要注册的密码
     * @return 是否注册成功
     */
    public boolean register(String username, String password);
}
