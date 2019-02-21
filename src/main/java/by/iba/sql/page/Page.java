package by.iba.sql.page;

import java.util.List;

public interface Page<T> {

	public long getTotalElement();
	
	public List<T> getContent();
	
}
