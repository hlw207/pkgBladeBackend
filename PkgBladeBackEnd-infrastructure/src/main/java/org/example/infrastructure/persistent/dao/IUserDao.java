package org.example.infrastructure.persistent.dao;


import org.apache.ibatis.annotations.Mapper;
import org.example.infrastructure.persistent.po.UserPO;

@Mapper
public interface IUserDao {
    void addUserUserEntity(String userName, String userKey);

    void updateUserEntity(String userName, String userKey);

    UserPO queryUserEntity(String userName);
}
