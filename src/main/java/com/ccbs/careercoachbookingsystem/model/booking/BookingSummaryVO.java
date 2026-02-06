package com.ccbs.careercoachbookingsystem.model.booking;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingSummaryVO {
    // 预约记录主键
    private Long id;
    // 预约状态（BookingStatusEnum）
    private String status;
    // 导师名称
    private String coachName;
    // 开始时间
    private LocalDateTime startTime;
    // 结束时间
    private LocalDateTime endTime;
    // 视频会议链接
    private String meetingUrl;
    // 取消链接
    private String cancelUrl;
	//会议全局id
	private String bookingUid;
}
