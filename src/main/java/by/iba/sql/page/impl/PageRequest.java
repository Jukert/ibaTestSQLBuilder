package by.iba.sql.page.impl;

import java.util.Arrays;
import java.util.List;

import by.iba.sql.page.Pageable;
import by.iba.sql.page.impl.Sort.Direction;
import by.iba.sql.page.impl.Sort.Order;

public class PageRequest implements Pageable {

	public static final Direction DEFAULT_DIRECTION = Direction.ASC;

	private final int page;
	private final int perPage;
	private final Sort sort;

	public PageRequest(int page, int size) {
		this(page, size, (Sort) null);
	}

	public PageRequest(int page, int size, String... properties) {
		this(page, size, Arrays.asList(properties));
	}

	public PageRequest(int page, int size, Order... properties) {
		this(page, size, new Sort(DEFAULT_DIRECTION, properties));
	}

	public PageRequest(int page, int size, List<String> properties) {
		this(page, size, new Sort(DEFAULT_DIRECTION, properties));
	}

	public PageRequest(int page, int size, Sort sort) {
		if (page < 0) {
			throw new IllegalArgumentException(
					"Page shouldn't be less than zero");
		}

		if (size < 0) {
			throw new IllegalArgumentException(
					"Size shouldn't be less than zero");
		}

		this.page = page;
		this.perPage = size;
		this.sort = sort;
	}

	public Sort getSort() {
		return sort;
	}

	public int getPage() {
		return page;
	}

	public int getPerPage() {
		return perPage;
	}

}
