package com.ccbs.careercoachbookingsystem.model.booking;

import lombok.Getter;

@Getter
public enum BookingStatusEnum {
    /** 初始状态 */
    PENDING,
    /** 支付成功且预约确认（Active） */
    BOOKING_CREATED,
    /** 预约已取消 */
    BOOKING_CANCELLED,
    /** 课程正常结束 */
    MEETING_ENDED,
    /** 用户或导师未出席 */
    NO_SHOW
}