package com.rmd.search.model;

import java.io.Serializable;

public class Option implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5805139532413595671L;
	private String id;
	private String name;
	public Option(String id, String name) {
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
