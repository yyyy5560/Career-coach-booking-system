package com.ccbs.careercoachbookingsystem.controller;

import com.ccbs.careercoachbookingsystem.model.booking.BookingParam;
import com.ccbs.careercoachbookingsystem.model.booking.BookingSummaryVO;
import com.ccbs.careercoachbookingsystem.model.booking.CancelBookingParam;
import com.ccbs.careercoachbookingsystem.service.BookingService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

/**
 * 预约相关接口：获取预约链接、查询我的预约、获取取消链接。
 */
@RestController
@RequestMapping
public class BookingController {

	@Resource
	private BookingService bookingService;
	
    /**
     * 功能 A: "去预约" (Booking API)
     * 获取 Cal.com 的预约链接。
     * `POST /api/booking-url`
     *
     * @param param userId
     * @return 真实可预约的 URL
     */
    @PostMapping("/booking-url")
    public String handleBookingUrlWebhook(@RequestBody BookingParam param) {
        requireUserId(param.getUserId());
        return bookingService.getBookingUrl(param.getUserId());
    }

    /**
     * 功能 B: "我的预约" 列表
     * `GET /api/bookings`
     *
     * @return 我的预约列表
     */
    @GetMapping("/bookings")
    public List<BookingSummaryVO> getMyBookings(@RequestParam String userId) {
        requireUserId(userId);
        return bookingService.getBookings(userId);
    }

    /**
     * 功能 C: "去取消预约"
     * `POST /api/bookings/cancel`
     */
    @PostMapping("/bookings/cancel")
    public String cancelBooking(@RequestBody CancelBookingParam param) {
        requireUserId(param.getUserId());
        return bookingService.getCancelUrl(param.getUserId(), param.getBookingId());
    }

    /**
     * 入参校验：本项目简化处理，只校验 userId 是否为空。
     */
    private void requireUserId(String userId) {
        if (!StringUtils.hasText(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId is required");
        }
    }
}
