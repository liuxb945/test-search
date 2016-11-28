package com.rmd.search.model;

import java.io.Serializable;
import java.util.List;

public class SearchList<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5770875677804666960L;
	private List<T> data;
	private Long total;
	private Integer pageSize;
	private Integer pageIndex;
	private List<Brand> brands;
	private List<PropertyType> propertyTypes;
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public List<Brand> getBrands() {
		return brands;
	}
	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}
	public List<PropertyType> getPropertyTypes() {
		return propertyTypes;
	}
	public void setPropertyTypes(List<PropertyType> propertyTypes) {
		this.propertyTypes = propertyTypes;
	}
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
