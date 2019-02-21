package by.iba.sql.functions;

import by.iba.sql.builder.impls.SqlBuilder;
import by.iba.sql.database.DataBase;
import by.iba.sql.page.Pageable;
import by.iba.sql.page.impl.Sort;

public class PaginationBuilder {

	private static DataBase dataBase;

	public static String paginate(Pageable pageable, String sql) {

		if (pageable == null) {
			return sql;
		}

		dataBase = SqlBuilder.getDataBase();
		StringBuilder sb = new StringBuilder(dataBase.getLimit()
				.getHeaderLimit());
		sb.append(sql);
		Sort sort = pageable.getSort();
		if (pageable.getSort() != null) {
			sb.append(" ORDER BY ");
			int size = sort.getOrders().size() - 1;
			for (int i = 0; i < size; i++) {
				sb.append(String.format(" %s, ", sort.getOrders().get(i)));
			}
			sb.append(sort.getOrders().get(size).getProperty() + " "
					+ sort.getDirection().name());
		}
		sb.append(String.format(dataBase.getLimit().getLimit(),
				pageable.getPage(), pageable.getPerPage()));
		return sb.toString();
	}

}
