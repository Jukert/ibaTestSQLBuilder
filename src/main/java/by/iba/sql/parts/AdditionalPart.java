package by.iba.sql.parts;

public class AdditionalPart {

	private String headerLimit = "";
	private String limit;
	private String order;

	public AdditionalPart() {
		// TODO Auto-generated constructor stub
	}

	public AdditionalPart(String headerLimit, String limit) {
		super();
		this.headerLimit = headerLimit;
		this.limit = limit;
	}

	public String getHeaderLimit() {
		return headerLimit;
	}

	public void setHeaderLimit(String headerLimit) {
		this.headerLimit = headerLimit;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
