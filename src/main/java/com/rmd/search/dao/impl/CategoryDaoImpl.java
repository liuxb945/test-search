package com.rmd.search.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.rmd.search.dao.CategoryDao;
import com.rmd.search.model.Category;

@Component("catDao")
public class CategoryDaoImpl implements CategoryDao {
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate template;

	public JdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public List<Category> loadByPid(Integer pid) throws Exception {
		String sql="select id,parentid pid,categoryname name,goodstypeid goodsTypeId from t_goods_category where parentid= "+pid;
		return (List<Category>)template.query(sql, new BeanPropertyRowMapper(Category.class));
	}

	@Override
	public List<Category> loadAll() {
		String sql="select id,categoryname name,parentid pid,goodstypeid goodsTypeId from t_goods_category";
		return (List<Category>)template.query(sql, new BeanPropertyRowMapper(Category.class));
	}

	@Override
	public Category loadById(Integer id) {
		String sql="select id,parentid pid,categoryname name,goodstypeid goodsTypeId from t_goods_category where id= ?";  
        return template.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(Category.class));
	}

}
