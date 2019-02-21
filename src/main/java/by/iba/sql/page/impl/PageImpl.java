package by.iba.sql.page.impl;

import java.io.Serializable;
import java.util.List;

import by.iba.sql.page.Page;
import by.iba.sql.page.Pageable;

public class PageImpl<T> implements Page<T>, Serializable{

	private static final long serialVersionUID = -6215365746495440538L;
	
	private final long total;
	private final List<T> content;
	private final Pageable pageable;

	public PageImpl(List<T> content, long total, Pageable pageable) {
		this.total = total;
		this.content = content;
		this.pageable = pageable;
	}

	public long getTotalElement() {
		return total;
	}
	
	public int getPage() {
		return pageable == null ? 0 : pageable.getPage();
	}

	public int getPerPage() {
		return pageable == null ? 0 : pageable.getPerPage();
	}

	public List<T> getContent() {
		return content;
	}

	public int getNumberOfElement() {
		return content.size();
	}

	public Sort getSort() {
		return pageable == null ? null : pageable.getSort();
	}

	public boolean hasContent() {
		return !content.isEmpty();
	}


}
