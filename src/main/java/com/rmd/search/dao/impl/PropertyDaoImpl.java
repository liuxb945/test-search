package com.rmd.search.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.rmd.search.dao.PropertyDao;
import com.rmd.search.model.PropertyItem;

@Component("propertyDao")
public class PropertyDaoImpl implements PropertyDao {
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate template;

	public JdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public List<PropertyItem> loadPropsByGoodsId(Long goodsId) {
		String sql="select id,goodsbaseid goodsId,goodstype goodsTypeId,goodstypepropertyid propertyTypeId, propertyselectid value from t_goods_property where goodsbaseid="+goodsId+" and propertyselectid is not null order by propertyselectid asc";
		return (List<PropertyItem>)template.query(sql, new BeanPropertyRowMapper(PropertyItem.class));
	}

}
