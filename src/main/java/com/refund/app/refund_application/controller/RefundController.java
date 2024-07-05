package com.refund.app.refund_application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.refund.app.refund_application.models.Refund;
import com.refund.app.refund_application.models.RefundRequest;
import com.refund.app.refund_application.services.RefundService;

@RestController
@RequestMapping("/refunds")
public class RefundController {
	@Autowired
	private RefundService refundService;

	@PostMapping("/returnRefund")
	public ResponseEntity<Refund> createRefund(@RequestBody RefundRequest refundRequest) {
		try {
			Refund refund = refundService.createRefund(refundRequest);
			return ResponseEntity.ok(refund);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}
	
	@GetMapping("/showReturnItems")
	public ResponseEntity<Refund> showReturnItems(@RequestBody RefundRequest refundRequest) {
		try {
			Refund refund = refundService.showItemsCanRefund(refundRequest);
			return ResponseEntity.ok(refund);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}
}
