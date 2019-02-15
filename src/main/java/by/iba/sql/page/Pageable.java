package by.iba.sql.page;

import by.iba.sql.page.impl.Sort;

public interface Pageable {

	int getPage();
	
	int getPerPage();
		
	Sort getSort();
	
}
