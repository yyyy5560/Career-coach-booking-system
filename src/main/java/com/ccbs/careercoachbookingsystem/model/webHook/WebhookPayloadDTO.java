package com.ccbs.careercoachbookingsystem.model.webHook;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class WebhookPayloadDTO {
	
    private String uid;         
    private String title;       
    private String startTime;
    private String endTime;

    // 导师信息
    private Advisor organizer;

    // 参与者列表
    private List<Attendee> attendees;
	
    private Map<String, Object> metadata;
}
