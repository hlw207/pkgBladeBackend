package org.example.domain.User.service;


import org.example.domain.User.repository.IUserRepo;
import org.example.domain.Utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private IUserRepo iUserRepo;

    public boolean login(String username, String password) {
        // 验证用户名和密码
       return passwordEncoder.matches(password, passwordEncoder.encode(password));
    }

    @Override
    public boolean register(String username, String password) {
        try {
            iUserRepo.addUserUserEntity(username, passwordEncoder.encode(password));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}