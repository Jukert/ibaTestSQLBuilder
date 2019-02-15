package by.iba.sql.page;

import java.util.List;

import by.iba.sql.page.impl.Sort;

public interface Slice<T> {

	public int getPage();
	
	public int getPerPage();
	
	public List<T> getContent();
	
	public int getNumberOfElement();
	
	public Sort getSort();
	
	public boolean hasContent();
	
}
