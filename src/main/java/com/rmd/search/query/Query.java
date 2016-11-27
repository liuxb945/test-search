package com.rmd.search.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.FacetParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rmd.search.dao.CategoryDao;
import com.rmd.search.dao.PropertyDao;
import com.rmd.search.model.Category;
import com.rmd.search.model.Goods;
import com.rmd.search.model.Group;
import com.rmd.search.model.PropertyItem;
import com.rmd.search.model.PropertyType;
import com.rmd.search.model.SearchList;
import com.rmd.search.utils.SolrUtil;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Component("query")
public class Query {
	
	@Resource(name="catDao")
	private CategoryDao catDao;
	
	@Resource(name="propertyDao")
	private PropertyDao propertyDao;
	
	private static HashMap<Integer, Category> mapCategory = new HashMap<Integer, Category>();
	private static HashMap<Integer, PropertyType> mapPropertyType = new HashMap<Integer, PropertyType>();
	
	public void init() throws Exception {
		if(mapCategory.size()>0){
			return;
		}
		List<Category> list=this.catDao.loadAll();
		for(Category c:list){
			mapCategory.put(c.getId(), c);
		}
		if(mapPropertyType.size()>0){
			return;
		}
		List<PropertyType> propTypeList=this.propertyDao.loadAllPropertyTypes();
		for(PropertyType pt:propTypeList){
			mapPropertyType.put(pt.getId(), pt);
			List<PropertyItem> items=this.propertyDao.loadPropertyItmesByPropTypeId(pt.getId());
			pt.setPropItems(items);
		}
	}
	@Test
	public void test(){
		HttpSolrServer core=SolrUtil.getServer();
	}
	@Test
	public void search() throws Exception {
		HttpSolrServer core=SolrUtil.getServer();
		SolrQuery query = new SolrQuery();
//        query.setQuery("*:*");
		query.setQuery("name:(奥美康)");
		System.out.println("SolrQuery: " +query.toString());
        query.setStart(0); // query的开始行数(分页使用)
        query.setRows(100); // query的返回行数(分页使用)
        query.setFacet(true); // 设置使用facet
        query.setFacetMinCount(1); // 设置facet最少的统计数量
        query.setFacetLimit(100); // facet结果的返回行数
        query.addFacetField("threeCategoryId","twoCategoryId","oneCategoryId", "brandId"); // facet的字段
        query.setFacetSort(FacetParams.FACET_SORT_COUNT);
        //query.addFilterQuery("twoCategoryId:178");
        query.addSort(new SortClause("id", ORDER.asc)); // 排序
        QueryResponse response = core.query(query);
        List<Goods> items_rep = response.getBeans(Goods.class);
        List<FacetField> facetFields = response.getFacetFields();
        // 因为上面的start和rows均设置为0，所以这里不会有query结果输出
        System.out.println("--------------------");
        System.out.println("Search result:");
        for (Goods item : items_rep) {
            System.out.println(item);
        }
        // 打印所有facet
        for (FacetField ff : facetFields) {
            System.out.println("--------------------");
            System.out.println("name=" + ff.getName() + "\tcount=" + ff.getValueCount());
            System.out.println("--------------------");
            List<Count> counts=ff.getValues();
            
            for(Count count:counts){
//            	System.out.println("name=" + mapCategory.get(count.getName()).getName() + "\tcount=" + count.getCount());
        		System.out.println("id=" + count.getName()+"\tname="+mapCategory.get(Integer.parseInt(count.getName())).getName() + "\tcount=" + count.getCount());
            }
            
//            switch (ff.getName()) {
//            case "regionId":
//                printOut(mapRegion, ff.getValues());
//                break;
//            case "categoryId":
//                printOut(mapCategory, ff.getValues());
//                break;
//            }
        }
	}
	
	public SearchList<Goods> query(String catId1,String catId2,String catId3,String sortBy,String s_w,String ev) throws Exception {
		HttpSolrServer core=SolrUtil.getServer();
		SolrQuery query = new SolrQuery();
		if(StringUtils.isNotEmpty(s_w)){
			query.setQuery("+name:"+s_w );
//			String params = "(title:笔记 OR content:笔记) AND catalog_id:2";
//			SolrQuery query = new SolrQuery();
//			query.setQuery(params);
		}
		else
		{
			query.setQuery("*:*");
		}
		query.setParam("q.op", "OR");
        query.setParam("df", "name");
		query.setHighlight(true); // 开启高亮组件或用query.setParam("hl", "true");  
        query.addHighlightField("name");// 高亮字段  
        query.setHighlightSimplePre("<font color='red'>");//标记，高亮关键字前缀  
        query.setHighlightSimplePost("</font>");//后缀 
        query.setStart(0); // query的开始行数(分页使用)
        query.setRows(1000); // query的返回行数(分页使用)
        query.setFacet(true); // 设置使用facet
        query.setFacetMinCount(1); // 设置facet最少的统计数量
        query.setFacetLimit(100); // facet结果的返回行数
        query.addFacetField("threeCategoryId","twoCategoryId","oneCategoryId", "brandId","propVals"); // facet的字段
        query.setFacetSort(FacetParams.FACET_SORT_COUNT);
        if(StringUtils.isNotEmpty(catId3)){
        	query.addFilterQuery("threeCategoryId:"+catId3);
        }else if(StringUtils.isNotEmpty(catId2)){
        	query.addFilterQuery("twoCategoryId:"+catId2);
        }else if(StringUtils.isNotEmpty(catId1)){
        	query.addFilterQuery("oneCategoryId:"+catId1);
        }
        TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>();
        if(StringUtils.isNotEmpty(ev)){
        	if(ev.indexOf(":")>0){
        		String[] kvs=ev.split(":");
        		String str="*";
        		for(String kv:kvs){
        			String[] arr=kv.split("_");
        			treeMap.put(Integer.parseInt(arr[0]), kv);
        		}
        		for(Entry<Integer,String> entry:treeMap.entrySet()){
        			str+=entry.getValue()+"*";
        		}
        		ev=str;
        	}else{
        		ev="*"+ev+"*";
        	}
        	
        	query.addFilterQuery("propVals:"+ev);
        	System.out.println("propVals:"+ev);
        }
        if(StringUtils.isNotEmpty(sortBy)){
        	query.addSort(new SortClause(sortBy, ORDER.desc)); // 排序
        }else{
//        	query.addSort(new SortClause("id", ORDER.asc)); // 排序
        }
        
        QueryResponse response = core.query(query);
        List<Goods> items_rep = response.getBeans(Goods.class);
        List<FacetField> facetFields = response.getFacetFields();
        // 因为上面的start和rows均设置为0，所以这里不会有query结果输出
        System.out.println("--------------------");
        System.out.println("Search result:");
        for (Goods item : items_rep) {
            System.out.println(item);
        }
        Map<String, Map<String, List<String>>> hightlights = response.getHighlighting();
        for(Goods g:items_rep){
        	Map<String,List<String>> hlMap=hightlights.get(String.valueOf(g.getId()));
        	if(hlMap!=null){
        		List<String> hlList=hlMap.get("name");
        		if(hlList!=null&&hlList.size()>0){
        			g.setName(hlList.get(0));
        		}
        	}
        }
        init();
        List<Group> groupList=new ArrayList<Group>();
     // 打印所有facet
        for (FacetField ff : facetFields) {
            System.out.println("--------------------");
            System.out.println("name=" + ff.getName() + "\tcount=" + ff.getValueCount());
            System.out.println("--------------------");
            List<Count> counts=ff.getValues();
            Group pg=new Group();
            if(ff.getName().equals("threeCategoryId")){
            	pg.setName(ff.getName());
            	pg.setCount(Long.valueOf(ff.getValueCount()));
            	List<Group> cgs=new ArrayList<Group>();
            	pg.setChildren(cgs);
            	groupList.add(pg);
            }
            
            for(Count count:counts){
            	if(ff.getName().equals("brandId")){
//            		System.out.println("name=" + count.getName() + "\tcount=" + count.getCount());
            	}else if(ff.getName().equals("threeCategoryId"))
            	{
            		String strName=mapCategory.get(Integer.parseInt(count.getName())).getName();
            		System.out.println("id=" + count.getName()+"\tname="+strName + "\tcount=" + count.getCount());
            		Group cg=new Group();
            		cg.setId(count.getName());
            		cg.setName(strName);
            		cg.setCount(count.getCount());
            		pg.getChildren().add(cg);
            	}
            	else if(ff.getName().equals("propVals")){
            		Map<String,PropertyType> tempPropTypeMap=new HashMap<String,PropertyType>();
            		String kvs=count.getName();
            		Map<String,List<Integer>> kvMap=new HashMap<String,List<Integer>>();
            		if(StringUtils.isNotEmpty(kvs))
            		{
            			String[] kvArrs=kvs.split(",");
            			for(String kv:kvArrs){
            				String[] pair=kv.split("_");
            				String key=pair[0];
            				if(!tempPropTypeMap.containsKey(key)){
            					PropertyType pt=mapPropertyType.get(key);
        	            		PropertyType pt1=new PropertyType();
        	            		BeanUtils.copyProperties(pt, pt1);
        	            		pt1.setPropItems(new ArrayList<PropertyItem>());
        	            		tempPropTypeMap.put(key, pt1);
            				}
            				PropertyType pt=tempPropTypeMap.get(key);
            			}
            			
            		}
            	}
            	else{
            		System.out.println("id=" + count.getName() + "\tcount=" + count.getCount());
            	}
            }
            
        }
        SearchList<Goods> searchList=new SearchList<>();
        searchList.setData(items_rep);
        searchList.setGroups(groupList);
        return searchList;
	}

}
