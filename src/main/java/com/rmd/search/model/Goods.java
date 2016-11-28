package com.rmd.search.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.beans.Field;

public class Goods implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8449494097725148313L;
	@Field
	private Long id;
	@Field
	private String name;
	@Field
	private String subname;
	@Field
	private Long oneCategoryId;
	@Field
	private String oneCategoryName;
	@Field
	private Long twoCategoryId;
	@Field
	private String twoCategoryName;
	@Field
	private Long threeCategoryId;
	@Field
	private String threeCategoryName;
	//@Field
	private List<PropertyItem> propList;
	@Field
	private Long brandId;
	@Field
	private String brandName;
	public List<PropertyItem> getPropList() {
		return propList;
	}
	public void setPropList(List<PropertyItem> propList) {
		this.propList = propList;
	}
	@Field
	private Date indbtime;
	@Field
	private Date modifytime;
	@Field
	private Double prefprice;
	@Field
	private String code;
	@Field
	private String propVals;
	@Field
	private String imgurl;
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getPropVals() {
		return propVals;
	}
	@Override
	public String toString() {
		return "Goods [id=" + id + ", name=" + name + ", subname=" + subname + ", oneCategoryId=" + oneCategoryId
				+ ", oneCategoryName=" + oneCategoryName + ", twoCategoryId=" + twoCategoryId + ", twoCategoryName="
				+ twoCategoryName + ", threeCategoryId=" + threeCategoryId + ", threeCategoryName=" + threeCategoryName
				+ ", propList=" + propList + ", brandId=" + brandId + ", brandName=" + brandName + ", indbtime="
				+ indbtime + ", modifytime=" + modifytime + ", prefprice=" + prefprice + ", code=" + code
				+ ", propVals=" + propVals + ", imgurl=" + imgurl + "]";
	}
	public void setPropVals(String propVals) {
		this.propVals = propVals;
	}
	public Double getPrefprice() {
		return prefprice;
	}
	public void setPrefprice(Double prefprice) {
		this.prefprice = prefprice;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubname() {
		return subname;
	}
	public void setSubname(String subname) {
		this.subname = subname;
	}
	public Long getOneCategoryId() {
		return oneCategoryId;
	}
	public void setOneCategoryId(Long oneCategoryId) {
		this.oneCategoryId = oneCategoryId;
	}
	public String getOneCategoryName() {
		return oneCategoryName;
	}
	public void setOneCategoryName(String oneCategoryName) {
		this.oneCategoryName = oneCategoryName;
	}
	public Long getTwoCategoryId() {
		return twoCategoryId;
	}
	public void setTwoCategoryId(Long twoCategoryId) {
		this.twoCategoryId = twoCategoryId;
	}
	public String getTwoCategoryName() {
		return twoCategoryName;
	}
	public void setTwoCategoryName(String twoCategoryName) {
		this.twoCategoryName = twoCategoryName;
	}
	public Long getThreeCategoryId() {
		return threeCategoryId;
	}
	public void setThreeCategoryId(Long threeCategoryId) {
		this.threeCategoryId = threeCategoryId;
	}
	public String getThreeCategoryName() {
		return threeCategoryName;
	}
	public void setThreeCategoryName(String threeCategoryName) {
		this.threeCategoryName = threeCategoryName;
	}
	public List<PropertyItem> getPropMap() {
		return propList;
	}
	public void setPropMap(List<PropertyItem> propList) {
		this.propList = propList;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Date getIndbtime() {
		return indbtime;
	}
	public void setIndbtime(Date indbtime) {
		this.indbtime = indbtime;
	}
	public Date getModifytime() {
		return modifytime;
	}
	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

}
