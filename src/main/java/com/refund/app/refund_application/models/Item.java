package com.refund.app.refund_application.models;

import org.springframework.stereotype.Component;

@Component
public class Item {
    private Long id;
    private String name;
    private int quantity;
    private double price;
    private double shippingCharge;

    // Private constructor to be called by the Builder
    private Item(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.quantity = builder.quantity;
        this.price = builder.price;
        this.shippingCharge = builder.shippingCharge;
    }

    // Builder class
    public static class Builder {
        private Long id;
        private String name;
        private int quantity;
        private double price;
        private double shippingCharge;

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder shippingCharge(double shippingCharge) {
            this.shippingCharge = shippingCharge;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getShippingCharge() {
		return shippingCharge;
	}

	public void setShippingCharge(double shippingCharge) {
		this.shippingCharge = shippingCharge;
	}
    
    
}

