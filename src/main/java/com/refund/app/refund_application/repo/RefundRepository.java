package com.refund.app.refund_application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refund.app.refund_application.models.RefundRequest;

@Repository
public interface RefundRepository extends JpaRepository<RefundRequest, Long> { 
	// Additional query methods if needed 
} 

