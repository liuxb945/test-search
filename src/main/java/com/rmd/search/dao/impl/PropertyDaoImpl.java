package com.rmd.search.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.rmd.search.dao.PropertyDao;
import com.rmd.search.model.PropertyItem;
import com.rmd.search.model.PropertyType;

@SuppressWarnings({ "unchecked", "rawtypes" })
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
	
	@Override
	public List<PropertyType> loadPropertyTypesByGoodTypeId(Integer goodTypeId) {
		String sql="select id,name from t_goods_type_property where recordmode=2 and issearch=true and goodstypeid=?";
		return (List<PropertyType>)template.query(sql,new Object[] { goodTypeId }, new BeanPropertyRowMapper(PropertyType.class));
	}

	@Override
	public List<PropertyItem> loadPropertyItmesByPropTypeId(Integer propTypeId) {
		String sql="select id,propertyname name from t_goods_type_property_param where goodstypepropertyid=?";
		return (List<PropertyItem>)template.query(sql,new Object[] { propTypeId }, new BeanPropertyRowMapper(PropertyItem.class));
	}

	@Override
	public PropertyType loadPropertyTypeById(Integer propTypeId) {
		String sql="select id,name from t_goods_type_property where id=?";
		return template.queryForObject(sql, new Object[] { propTypeId }, new BeanPropertyRowMapper(PropertyType.class));
	}

	@Override
	public List<PropertyType> loadAllPropertyTypes() {
		String sql="select id,name from t_goods_type_property where recordmode=2 and issearch=true";
		return (List<PropertyType>)template.query(sql,new BeanPropertyRowMapper(PropertyType.class));
	}

	@Override
	public List<PropertyItem> loadAllPropertyItmes() {
		String sql="select id,propertyname name,goodstypepropertyid propertyTypeId from t_goods_type_property_param";
		return (List<PropertyItem>)template.query(sql, new BeanPropertyRowMapper(PropertyItem.class));
	}

}
