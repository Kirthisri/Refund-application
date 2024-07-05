package com.refund.app.refund_application.models;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class RefundRequest implements Cloneable{
    private Long orderId;
    private List<RefundItems> returnItems;
    private String paymentReferenceId;

    // Private constructor to be called by the Builder
    private RefundRequest(Builder builder) {
        this.orderId = builder.orderId;
        this.returnItems = builder.returnItems;
        this.paymentReferenceId = builder.paymentReferenceId;
    }

    // Builder class
    public static class Builder {
        private Long orderId;
        private List<RefundItems> returnItems;
        private String paymentReferenceId;

        public Builder() {
        }

        public Builder orderId(Long orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder returnItems(List<RefundItems> returnItems) {
            this.returnItems = returnItems;
            return this;
        }

        public Builder paymentReferenceId(String paymentReferenceId) {
            this.paymentReferenceId = paymentReferenceId;
            return this;
        }

        public RefundRequest build() {
            return new RefundRequest(this);
        }
    }

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public List<RefundItems> getReturnItems() {
		return returnItems;
	}

	public void setReturnItems(List<RefundItems> returnItems) {
		this.returnItems = returnItems;
	}

	public String getPaymentReferenceId() {
		return paymentReferenceId;
	}

	public void setPaymentReferenceId(String paymentReferenceId) {
		this.paymentReferenceId = paymentReferenceId;
	}
    
    @Override
    public RefundRequest clone() {
        try {
            RefundRequest cloned = (RefundRequest) super.clone();
            cloned.returnItems = this.returnItems.stream()
                                                  .map(RefundItems::clone)
                                                  .collect(Collectors.toList());
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

