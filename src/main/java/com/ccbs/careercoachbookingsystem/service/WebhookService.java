package com.ccbs.careercoachbookingsystem.service;

import com.ccbs.careercoachbookingsystem.entity.BookingEntity;
import com.ccbs.careercoachbookingsystem.mapper.BookingMapper;
import com.ccbs.careercoachbookingsystem.model.webHook.Attendee;
import com.ccbs.careercoachbookingsystem.model.booking.BookingStatusEnum;
import com.ccbs.careercoachbookingsystem.model.webHook.WebhookPayloadDTO;
import com.ccbs.careercoachbookingsystem.model.webHook.WebhookRequestParam;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.Map;

@Slf4j
@Service
public class WebhookService {
    
	@Resource
    private  BookingMapper bookingMapper;
	@Resource
    private  UserService userService;
	@Resource
    private  ObjectMapper objectMapper;
    @Value("${wisp.cal.cancelUrl}")
    private String cancelUrl;
	
    /**
     * 处理 Cal.com Webhook 回调
     * 根据 triggerEvent 做状态分发
     */
    @Transactional
    public Boolean handleWebhook(WebhookRequestParam request) {
        String event = request.getTriggerEvent();
        WebhookPayloadDTO payload = request.getPayload();
        
        // 处理 "预约创建" 事件
        if (BookingStatusEnum.BOOKING_CREATED.toString().equals(event)) {
            processBookingCreated(payload);
        }
        // 处理 "预约取消" 事件
        else if (BookingStatusEnum.BOOKING_CANCELLED.toString().equals(event)) {
            processBookingCancelled(payload);
        }
        else {
			throw new RuntimeException("不支持的 Webhook 事件: " + event);
        }
		
		return true;
    }
    
    /**
     * 处理预约创建事件：
     * - 生成并保存 Booking 记录
     */
    private void processBookingCreated(WebhookPayloadDTO payload) {
		
        String userId = extractMetadata(payload.getMetadata(), "userId");
        if (userId == null || userId.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "参数错误");
        }
        
        // 获取用户信息（从第一个 attendee）
        if (payload.getAttendees() == null || payload.getAttendees().isEmpty()) {
            return;
        }
        
        Attendee attendee = payload.getAttendees().getFirst();
        
        // 确保用户存在
        userService.ensureUserExists(userId, attendee.getName(), attendee.getEmail());
        
        // 检查是否已存在该预约（通过 uid）防止重复创建
        String bookingUid = payload.getUid();
        BookingEntity existingBooking = bookingMapper.findByBookingUid(bookingUid);
        
        if (existingBooking != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "预约已存在,请勿重复预约");
        }
        
        BookingEntity booking = new BookingEntity();
        
        // 基础信息
        booking.setUserId(userId);
        booking.setBookingUid(bookingUid);
        booking.setStatus(BookingStatusEnum.BOOKING_CREATED.toString());
        
        // 导师信息（由 Cal.com 分配的 organizer）
        if (payload.getOrganizer() != null) {
            booking.setCoachName(payload.getOrganizer().getName());
            booking.setCoachEmail(payload.getOrganizer().getEmail());
        }
        if (payload.getStartTime() != null) {
            booking.setStartTime(ZonedDateTime.parse(payload.getStartTime()).toLocalDateTime());
        }
        if (payload.getEndTime() != null) {
            booking.setEndTime(ZonedDateTime.parse(payload.getEndTime()).toLocalDateTime());
        }
        
        // 取消链接
        booking.setCancelUrl(getCancelUrl(bookingUid));
        
        // 视频会议链接
        String meetingUrl = extractMetadata(payload.getMetadata(), "videoCallUrl");
        booking.setMeetingUrl(meetingUrl);
		
        try {
            booking.setRawWebhookData(objectMapper.writeValueAsString(payload));
        } catch (Exception e) {
            log.warn("序列化 webhook 数据失败", e);
        }
		
        try {
            bookingMapper.insert(booking);
        } catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "保存预约失败");
        }
    }
	
	
	private String getCancelUrl(String bookingUid){
		return cancelUrl.replace("{booking_uid}", bookingUid);
	}
    
    /**
     * 处理预约取消事件,更新状态
     */
    private void processBookingCancelled(WebhookPayloadDTO payload) {
        String bookingUid = payload.getUid();
        
        BookingEntity booking = bookingMapper.findByBookingUid(bookingUid);
        if (booking == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "取消失败，未找到预约记录");
        }
		
        booking.setStatus(BookingStatusEnum.BOOKING_CANCELLED.toString());
        
        // 保存原始回调数据
        try {
            booking.setRawWebhookData(objectMapper.writeValueAsString(payload));
        } catch (Exception e) {
            log.warn("序列化 webhook 数据失败 ,原始回调数据保存失败", e);
        }
        bookingMapper.update(booking);
    }
    
    /**
     * 从 metadata 中提取指定字段。
     */
    private String extractMetadata(Map<String, Object> metadata, String key) {
        if (metadata == null) {
            return null;
        }
        Object value = metadata.get(key);
        return value != null ? value.toString() : null;
    }
}
