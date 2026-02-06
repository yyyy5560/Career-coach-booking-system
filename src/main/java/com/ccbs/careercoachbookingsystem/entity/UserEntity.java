package com.ccbs.careercoachbookingsystem.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserEntity {
	// 主键 id
    private Long id;
	// 用户全局 userId
    private String userId;
	// 姓名
    private String name;
	// 邮箱
    private String email;
    // 创建时间
    private LocalDateTime createdAt;
    // 更新时间
    private LocalDateTime updatedAt;
}
