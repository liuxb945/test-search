package com.rmd.search.dao;

import java.util.List;

import com.rmd.search.model.Goods;

public interface GoodsDao {
	List<Goods> loadGoods();

}
