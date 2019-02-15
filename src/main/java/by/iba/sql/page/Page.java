package by.iba.sql.page;

import java.util.List;

public interface Page<T> {

	public int getTotalElement();
	
	public List<T> getContent();
	
}
