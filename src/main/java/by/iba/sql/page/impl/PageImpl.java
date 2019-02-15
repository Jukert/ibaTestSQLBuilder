package by.iba.sql.page.impl;

import java.io.Serializable;
import java.util.List;

import by.iba.sql.page.Page;
import by.iba.sql.page.Pageable;

public class PageImpl<T> extends Chunk<T> implements Page<T>, Serializable{

	private static final long serialVersionUID = -6215365746495440538L;
	
	private final int total;

	public PageImpl(List<T> content, int total, Pageable pageable) {
		super(content, pageable);
		this.total = total;
	}

	public int getTotalElement() {
		return total;
	}

	public List<T> getContent() {
		return super.getContent();
	}

}
