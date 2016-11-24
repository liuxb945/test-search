package com.rmd.search.dao;

import java.util.List;

import com.rmd.search.model.PropertyItem;

public interface PropertyDao {
	List<PropertyItem> loadPropsByGoodsId(Long goodsId);
}
