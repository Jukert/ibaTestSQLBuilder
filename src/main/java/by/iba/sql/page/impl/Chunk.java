package by.iba.sql.page.impl;

import java.io.Serializable;
import java.util.List;

import by.iba.sql.page.Pageable;
import by.iba.sql.page.Slice;

public class Chunk<T> implements Slice<T>, Serializable {

	private static final long serialVersionUID = 502069154093320883L;
	
	private final List<T> content;
	private final Pageable pageable;

	public Chunk(List<T> content, Pageable pageable) {
		this.content = content;
		this.pageable = pageable;
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
