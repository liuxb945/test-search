package com.rmd.search.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rmd.search.dao.GoodsDao;
import com.rmd.search.dao.impl.GoodsDaoImpl;
import com.rmd.search.model.Goods;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})  
public class TestIndex {
	@Resource(name="goodsDao")
	private GoodsDao goodsDao;
	
	public GoodsDao getGoodsDao() {
		return goodsDao;
	}
	public void setGoodsDao(GoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}
	@Test
	public void testLoadGoods(){
		
		List<Goods> goodsList=goodsDao.loadGoods();
		for(Goods g:goodsList){
			System.out.println(g);
		}
	}
}
