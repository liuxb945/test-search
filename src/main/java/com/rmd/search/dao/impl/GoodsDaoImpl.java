package com.rmd.search.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.rmd.search.dao.GoodsDao;
import com.rmd.search.model.Goods;

@Component("goodsDao")
public class GoodsDaoImpl implements GoodsDao {
	@Resource(name="jdbcTemplate")
	private JdbcTemplate template;

	public JdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public List<Goods> loadGoods() {
		String sql="select g.id,g.code,g.imgurl,g.indbtime,g.modifytime,g.name,g.subname,gb.id brandId,gb.brandname brandName,gc.categoryname threeCategoryName,gc.id threeCategoryId,gc1.categoryname twoCategoryName,gc1.id twoCategoryId,gc2.categoryname oneCategoryName,gc2.id oneCategoryId from t_goods_base g left join t_goods_category gc on g.categoryid=gc.id left join t_goods_brand gb on g.brandid=gb.id left join t_goods_category gc1 on gc.parentid=gc1.id left join  t_goods_category gc2 on gc1.parentid=gc2.id ";
		return (List<Goods>)template.query(sql, new BeanPropertyRowMapper(Goods.class));
	}
	
	
}
