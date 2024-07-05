package com.refund.app.refund_application.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.refund.app.refund_application.models.Order; 

public interface OrderRepository extends JpaRepository<Order, Long> { 
	// Additional query methods if needed 
} 
