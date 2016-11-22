package com.rmd.search.dao.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.rmd.search.dao.CategoryDao;
import com.rmd.search.model.Category;
import com.rmd.search.utils.JdbcUtil;

@Component("catDao")
public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> loadByPid(Integer pid) throws Exception {
		String sql="select id,parentid pid,categoryname name from t_goods_category where parentid= "+pid;
		Connection conn = JdbcUtil.getConnection();
		List<HashMap> listMap = JdbcUtil.queryList(sql, conn, true);
		List<Category> list=new ArrayList<Category>();
		for (HashMap map : listMap) {
			Iterator iter = map.entrySet().iterator();
			Category category=new Category();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				Class c = Category.class;  
				Field f = c.getDeclaredField((String) key);
				f.setAccessible(true); //设置些属性是可以访问的
				f.set(category, val);
			}
			list.add(category);
		}
		return list;
	}

}
