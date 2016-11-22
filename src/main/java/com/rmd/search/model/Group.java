package com.rmd.search.model;

import java.io.Serializable;
import java.util.List;

public class Group implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3397890665388193940L;
	private String id;
	private String name;
	private Long count;
	private List<Group> children;
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
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public List<Group> getChildren() {
		return children;
	}
	public void setChildren(List<Group> children) {
		this.children = children;
	}
}
