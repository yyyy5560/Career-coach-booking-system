package com.ccbs.careercoachbookingsystem.entity;

import com.ccbs.careercoachbookingsystem.model.booking.BookingStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingEntity {
	// 主键
    private Long id;
    // 系统的用户ID
    private String userId;              
    // Cal.com 的唯一ID
    private String bookingUid;          
    // 导师姓名
    private String coachName;           
    // 导师邮箱
    private String coachEmail;          
	// 开始时间
    private LocalDateTime startTime;   
    // 结束时间
    private LocalDateTime endTime;
	/**
	 * 状态
	 * {@link BookingStatusEnum}
	 */
	private String status;              
    // 视频会议链接
    private String meetingUrl;          
    // 取消链接
    private String cancelUrl;           
    // 创建时间
    private LocalDateTime createdAt;
    // 更新时间
    private LocalDateTime updatedAt;
    // 原始 Webhook JSON 数据
    private String rawWebhookData;      
}
