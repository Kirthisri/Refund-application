package com.refund.app.refund_application.models;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Order {
    private Long id;
    private List<Item> orderedItems;
    private boolean completed;

    // Private constructor to be called by the Builder
    private Order(Builder builder) {
        this.id = builder.id;
        this.orderedItems = builder.orderedItems;
        this.completed = builder.completed;
    }

    // Builder class
    public static class Builder {
        private Long id;
        private List<Item> orderedItems;
        private boolean completed;

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder orderedItems(List<Item> orderedItems) {
            this.orderedItems = orderedItems;
            return this;
        }

        public Builder completed(boolean completed) {
            this.completed = completed;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Item> getOrderedItems() {
		return orderedItems;
	}

	public void setOrderedItems(List<Item> orderedItems) {
		this.orderedItems = orderedItems;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
    
    
}

