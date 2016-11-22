package com.rmd.search.model;

import java.io.Serializable;

public class PropItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7277985730536097829L;
	private Long id;
	private String propName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPropName() {
		return propName;
	}
	public void setPropName(String propName) {
		this.propName = propName;
	}
	public String getPropValue() {
		return propValue;
	}
	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}
	private String propValue;

}
