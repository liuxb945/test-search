package com.rmd.search.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rmd.search.dao.CategoryDao;
import com.rmd.search.model.Category;
import com.rmd.search.model.Goods;
import com.rmd.search.model.Option;
import com.rmd.search.model.SearchList;
import com.rmd.search.query.Query;

@Controller
@RequestMapping("/search")
public class SearchController {
	
	public static final Logger logger =Logger.getLogger(SearchController.class);
	
	@Resource(name="catDao")
	private CategoryDao catDao;
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
    public ModelAndView index(){
		ModelAndView modelAndView = new ModelAndView("firstIndex");
		List<Category> list=null;
		try {
			list=catDao.loadByPid(0);
		} catch (Exception e) {
			logger.error("", e);
		}
        modelAndView.addObject("items1", list);  
        return modelAndView;
    }
	
	@RequestMapping(value="/category1",method=RequestMethod.GET)
    public ModelAndView category1(@RequestParam("cat") int catId){
		ModelAndView modelAndView = new ModelAndView("category1");
		List<Category> list1=null;
		try {
			list1=catDao.loadByPid(0);
		} catch (Exception e) {
			logger.error("", e);
		}
		List<Category> list2=null;
		try {
			list2=catDao.loadByPid(catId);
		} catch (Exception e) {
			logger.error("", e);
		}
        modelAndView.addObject("items1", list1);
        modelAndView.addObject("items2", list2);
        List<Option> sorts=new ArrayList<Option>();
        sorts.add(new Option("","请选择"));
        sorts.add(new Option("id","id"));
        sorts.add(new Option("indbtime","日期"));
        modelAndView.addObject("sorts", sorts);
        List<Goods> goodsList=null;
        try {
			goodsList=Query.queryCategory1(String.valueOf(catId));
		} catch (Exception e) {
			logger.error("", e);
		}
        modelAndView.addObject("goodsList", goodsList);
        return modelAndView;
    }
	
	@RequestMapping(value="/category",method=RequestMethod.GET)
    public ModelAndView category(@RequestParam(value="cat1",required=false) String catId1,@RequestParam(value="cat2",required=false) String catId2,@RequestParam(value="cat3",required=false) String catId3,@RequestParam(value="sortby",required=false) String sortBy){
		ModelAndView modelAndView = new ModelAndView("category");
		List<Category> list1=new ArrayList<Category>();
		try {
			list1=catDao.loadByPid(0);
		} catch (Exception e) {
			logger.error("", e);
		}
		List<Category> list2=new ArrayList<Category>();
		try {
			if(StringUtils.isNotEmpty(catId1)){
				list2=catDao.loadByPid(Integer.parseInt(catId1));
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		List<Category> list3=null;
		try {
			if(StringUtils.isNotEmpty(catId2)){
				list3=catDao.loadByPid(Integer.parseInt(catId2));
			}
		} catch (Exception e) {
			logger.error("", e);
		}
        modelAndView.addObject("items1", list1);
        modelAndView.addObject("items2", list2);
        modelAndView.addObject("items3", list3);
        List<Option> sorts=new ArrayList<Option>();
        sorts.add(new Option("","请选择"));
        sorts.add(new Option("id","id"));
        sorts.add(new Option("indbtime","日期"));
        modelAndView.addObject("sorts", sorts);
        SearchList<Goods> searchList=null;
        try {
        	searchList=Query.query(catId1, catId2, catId3, sortBy);
		} catch (Exception e) {
			logger.error("", e);
		}
        modelAndView.addObject("searchList", searchList);
        return modelAndView;
    }
	
	public CategoryDao getCatDao() {
		return catDao;
	}
	public void setCatDao(CategoryDao catDao) {
		this.catDao = catDao;
	}

}
