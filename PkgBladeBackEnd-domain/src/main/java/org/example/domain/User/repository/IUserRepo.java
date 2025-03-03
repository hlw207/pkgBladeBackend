package org.example.domain.User.repository;

import org.example.domain.User.model.entity.UserEntity;

public interface IUserRepo {
    UserEntity queryUserEntity(String userName);

    void addUserUserEntity(String userName, String userKey);

    void updateUserEntity(String userName, String userKey);
}
