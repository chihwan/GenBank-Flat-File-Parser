package org.renci.gbff.model;

import java.io.Serializable;

public class Origin implements Serializable {

	private static final long serialVersionUID = 318127662439364742L;

	private Integer index;

	private String sequence;

	public Origin() {
		super();
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	@Override
	public String toString() {
		return String.format("Origin [index=%s, sequence=%s]", index, sequence);
	}

}
