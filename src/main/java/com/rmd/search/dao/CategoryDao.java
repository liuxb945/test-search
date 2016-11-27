package com.rmd.search.dao;

import java.util.List;

import com.rmd.search.model.Category;

public interface CategoryDao {
	public List<Category> loadByPid(Integer pid) throws Exception;
	public List<Category> loadAll();
	public Category loadById(Integer id);
}
