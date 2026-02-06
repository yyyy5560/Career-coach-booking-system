package com.ccbs.careercoachbookingsystem.mapper;

import com.ccbs.careercoachbookingsystem.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    // 根据 userId 查询用户
    UserEntity findByUserId(@Param("userId") String userId);
    // 新增用户
    int insert(UserEntity user);
}
