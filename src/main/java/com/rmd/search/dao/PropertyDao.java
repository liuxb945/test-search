package com.rmd.search.dao;

import java.util.List;

import com.rmd.search.model.PropertyItem;
import com.rmd.search.model.PropertyType;

public interface PropertyDao {
	List<PropertyItem> loadPropsByGoodsId(Long goodsId);
	List<PropertyType> loadPropertyTypesByGoodTypeId(Integer goodTypeId);
	List<PropertyItem> loadPropertyItmesByPropTypeId(Integer propTypeId);
	PropertyType loadPropertyTypeById(Integer propTypeId);
	List<PropertyType> loadAllPropertyTypes();
	List<PropertyItem> loadAllPropertyItmes();
}
