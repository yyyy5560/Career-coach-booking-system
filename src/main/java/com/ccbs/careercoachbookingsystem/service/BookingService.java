package com.ccbs.careercoachbookingsystem.service;

import com.ccbs.careercoachbookingsystem.entity.BookingEntity;
import com.ccbs.careercoachbookingsystem.mapper.BookingMapper;
import com.ccbs.careercoachbookingsystem.model.booking.BookingSummaryVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Value("${wisp.cal.baseUrl}")
    private String calBaseUrl;
    
	@Resource
	private BookingMapper bookingMapper;

    /**
     * 获取预约链接
     * 返回 Cal.com 的预约 URL，追加 metadata 以便 webhook 能回传 userId。
     */
    public String getBookingUrl(String userId) {
		return calBaseUrl+"?metadata[userId]="+userId;
    }
    
    /**
     * 获取用户的预约列表（按 userId 查询本地数据库）
     */
    public List<BookingSummaryVO> getBookings(String userId) {
        List<BookingEntity> bookings = bookingMapper.findByUserId(userId);
        return bookings.stream()
            .map(this::toSummary)
            .collect(Collectors.toList());
    }
    
    /**
     * 获取取消预约的链接
     * 仅当 booking 属于该 userId 时才返回 cancelUrl。
     */
    public String getCancelUrl(String userId, String bookingId) {
        BookingEntity booking = bookingMapper.findByBookingUid(bookingId);
        if (booking == null || !booking.getUserId().equals(userId)) {
            return null;
        }
        return booking.getCancelUrl();
    }
	
	
    private BookingSummaryVO toSummary(BookingEntity entity) {
        BookingSummaryVO summary = new BookingSummaryVO();
        BeanUtils.copyProperties(entity, summary);
        return summary;
    }
}
