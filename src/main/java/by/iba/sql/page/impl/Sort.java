package by.iba.sql.page.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Sort {

	private static final String DEFAULT_ORDER = "A.ACCT_ID";
	private static final Direction DEFAULT_DIRECTION = Direction.ASC;
	
	private final List<Order> orders;
	private final Direction direction;

	public Sort(Direction direction, String... properties) {
		this(direction, properties == null ? new ArrayList<String>() : Arrays
				.asList(properties));
	}

	public Sort(Direction direction, Order... properties) {
		if (properties == null) {
			this.orders = null;
		} else {
			this.orders = new ArrayList<Order>(properties.length);
			for (Order property : properties) {
				this.orders.add(property);
			}
		}
		this.direction = direction;
	}

	public Sort(Direction direction, List<String> properties) {

		if (properties == null || properties.isEmpty()) {
			this.orders = null;
		} else {
			this.orders = new ArrayList<Order>(properties.size());
			for (String property : properties) {
				this.orders.add(new Order(property));
			}
		}
		this.direction = direction;
	}

	public List<Order> getOrders() {
		return orders == null || orders.isEmpty() ? Arrays.asList(new Order(DEFAULT_ORDER)) : orders;
	}

	public Direction getDirection() {
		return direction == null ? DEFAULT_DIRECTION : direction;
	}

	public enum Direction {
		ASC, DESC;

		public boolean isAsceding() {
			return this.equals(ASC);
		}

		public boolean isDesceding() {
			return this.isDesceding();
		}

		public static Direction fromString(String value) {
			return value == null || value.isEmpty() ? null : Direction
					.valueOf(value.toUpperCase(Locale.US));

		}

		public static Direction fromStringOrNull(String value) {

			try {
				return fromString(value);
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
	}

	public static class Order {

		private final String property;

		public Order(String property) {
			this.property = property;
		}

		public String getProperty() {
			return property;
		}

	}

}
