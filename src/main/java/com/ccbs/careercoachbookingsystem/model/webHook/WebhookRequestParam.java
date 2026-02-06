package com.ccbs.careercoachbookingsystem.model.webHook;

import lombok.Data;

@Data
public class WebhookRequestParam {
	
    private String triggerEvent;
	
    private String createdAt;
	
    private WebhookPayloadDTO payload;
}

