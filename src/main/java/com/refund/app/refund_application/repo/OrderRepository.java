package com.refund.app.refund_application.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refund.app.refund_application.models.Order; 
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> { 
	// Additional query methods if needed 
} 
