package org.example.infrastructure.persistent.repository;

import org.example.domain.User.model.entity.UserEntity;
import org.example.domain.User.repository.IUserRepo;
import org.example.infrastructure.persistent.dao.IUserDao;
import org.example.infrastructure.persistent.po.UserPO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;


@Repository
public class UserRepo implements IUserRepo {
    @Resource
    private IUserDao userDao;
    @Override
    public UserEntity queryUserEntity(String userName) {
        UserPO userPO = userDao.queryUserEntity(userName);
        return UserEntity.builder().userName(userName).userKey(userPO.getUserKey()).userId(userPO.getUserId()).build();
    }

    @Override
    public void addUserUserEntity(String userName, String userKey) {
        userDao.addUserUserEntity(userName, userKey);
    }

    @Override
    public void updateUserEntity(String userName, String userKey) {
        userDao.updateUserEntity(userName, userKey);
    }
}
