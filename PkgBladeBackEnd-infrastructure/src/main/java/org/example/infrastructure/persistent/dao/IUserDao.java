package org.example.infrastructure.persistent.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.infrastructure.persistent.po.UserPO;

@Mapper
public interface IUserDao {
    void addUserUserEntity(@Param("userName") String userName, @Param("userKey") String userKey);

    void updateUserEntity(@Param("userName") String userName, @Param("userKey") String userKey);

    UserPO queryUserEntity(@Param("userName") String userName);
}
