package com.refund.app.refund_application.models;

import org.springframework.stereotype.Component;

@Component
public class RefundItems implements Cloneable{
	private Long id;
	private String name;
	private int quantity;
	private boolean fullyRefunded;
	private boolean partiallyRefunded;

	private RefundItems(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.quantity = builder.quantity;
		this.fullyRefunded = builder.fullyRefunded;
		this.partiallyRefunded = builder.partiallyRefunded;
	}

	public static class Builder {
		private Long id;
		private String name;
		private int quantity;
		private boolean fullyRefunded;
		private boolean partiallyRefunded;

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

		public Builder fullyRefunded(boolean fullyRefunded) {
			this.fullyRefunded = fullyRefunded;
			return this;
		}

		public Builder partiallyRefunded(boolean partiallyRefunded) {
			this.partiallyRefunded = partiallyRefunded;
			return this;
		}

		public RefundItems build() {
			return new RefundItems(this);
		}
	}

	// getters and setters
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

	public boolean isFullyRefunded() {
		return fullyRefunded;
	}

	public void setFullyRefunded(boolean fullyRefunded) {
		this.fullyRefunded = fullyRefunded;
	}

	public boolean isPartiallyRefunded() {
		return partiallyRefunded;
	}

	public void setPartiallyRefunded(boolean partiallyRefunded) {
		this.partiallyRefunded = partiallyRefunded;
	}

    @Override
    public RefundItems clone() {
        try {
            return (RefundItems) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Should never happen
        }
    }
}
