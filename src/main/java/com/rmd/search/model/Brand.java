package com.rmd.search.model;

import java.io.Serializable;

public class Brand implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7814881534931312132L;
	private Integer id;
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
