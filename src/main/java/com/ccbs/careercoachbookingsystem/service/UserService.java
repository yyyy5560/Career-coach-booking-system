package com.ccbs.careercoachbookingsystem.service;

import com.ccbs.careercoachbookingsystem.entity.UserEntity;
import com.ccbs.careercoachbookingsystem.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
	
    /**
    * 确保用户存在：如果不存在则创建，用于缓存 Cal.com 回调的用户信息。
     */
    public void ensureUserExists(String userId, String name, String email) {
        UserEntity existingUser = userMapper.findByUserId(userId);
        if (existingUser == null) {
            UserEntity newUser = new UserEntity();
            newUser.setUserId(userId);
            newUser.setName(name);
            newUser.setEmail(email);
            userMapper.insert(newUser);
        }
    }
    
    /**
     * 获取用户信息
     */
    public UserEntity getUserByUserId(String userId) {
        return userMapper.findByUserId(userId);
    }
}
