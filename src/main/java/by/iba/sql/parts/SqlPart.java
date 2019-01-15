package by.iba.sql.parts;

import java.util.ArrayList;
import java.util.List;

import by.iba.sql.parts.child.ExpressionChild;

public class SqlPart {

	private String headerPart;
	private List<ExpressionChild> child;
	private AdditionalPart additionalPart;
	
	public SqlPart(String headerPart) {
		this.headerPart = headerPart;
		child = new ArrayList<ExpressionChild>();
		additionalPart = new AdditionalPart();	
	}

	public String getHeaderPart() {
		return headerPart;
	}

	public void setHeaderPart(String headerPart) {
		this.headerPart = headerPart;
	}

	public List<ExpressionChild> getChild() {
		return child;
	}

	public void setChild(List<ExpressionChild> child) {
		this.child = child;
	}

	public AdditionalPart getAdditionalPart() {
		return additionalPart;
	}

	public void setAdditionalPart(AdditionalPart additionalPart) {
		this.additionalPart = additionalPart;
	}
}
