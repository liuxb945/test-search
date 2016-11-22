package com.rmd.search.model;

import java.io.Serializable;
import java.util.List;

public class SearchList<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5770875677804666960L;
	private List<T> data;
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	private List<Group> groups;
}
