package com.ccbs.careercoachbookingsystem.controller;

import com.ccbs.careercoachbookingsystem.model.webHook.WebhookRequestParam;
import com.ccbs.careercoachbookingsystem.service.WebhookService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 接收第三方回调
 */
@RestController
@RequestMapping("/webhook")
public class CallBackController {
	
	@Resource
	private WebhookService webhookService;
	
	/**
	 * 功能 D: 接收预约和取消回调
	 */
	@PostMapping("/cal")
	public Boolean handleCalWebHook(@RequestBody WebhookRequestParam request) {
		return webhookService.handleWebhook(request);
	}
	
}

