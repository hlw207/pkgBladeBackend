package org.example.domain.User.service;

import org.example.domain.User.repository.IUserRepo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService{
    @Resource
    private IUserRepo iUserRepo;

    @Override
    public Long getUserIdByName(String username) {
        return iUserRepo.queryUserEntity(username).getUserId();
    }
}
