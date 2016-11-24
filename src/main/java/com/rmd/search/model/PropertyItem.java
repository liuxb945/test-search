package com.rmd.search.model;

import java.io.Serializable;

public class PropertyItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7277985730536097829L;
	private Integer id;
	private String name;
	private String value;
	private Long goodsId;
	private Integer propertyTypeId;
	private Integer goodsTypeId;
	private Integer saleid;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "PropertyItem [id=" + id + ", name=" + name + ", value=" + value + ", goodsId=" + goodsId
				+ ", propertyTypeId=" + propertyTypeId + ", goodsTypeId=" + goodsTypeId + ", saleid=" + saleid + "]";
	}
	public String toPropString(){
		return String.format("%s_%s", this.propertyTypeId,this.value);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getPropertyTypeId() {
		return propertyTypeId;
	}
	public void setPropertyTypeId(Integer propertyTypeId) {
		this.propertyTypeId = propertyTypeId;
	}
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getGoodsTypeId() {
		return goodsTypeId;
	}
	public void setGoodsTypeId(Integer goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}
	public Integer getSaleid() {
		return saleid;
	}
	public void setSaleid(Integer saleid) {
		this.saleid = saleid;
	}

}
