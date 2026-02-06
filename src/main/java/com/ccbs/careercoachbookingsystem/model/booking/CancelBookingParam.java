package com.ccbs.careercoachbookingsystem.model.booking;

import lombok.Data;

@Data
public class CancelBookingParam {
    // 业务系统 userId
    private String userId;
    // Cal.com booking uid
    private String bookingId;
}
