package com.rmd.search.query;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.rmd.search.model.Category;
import com.rmd.search.model.Goods;
import com.rmd.search.model.Group;
import com.rmd.search.model.SearchList;
import com.rmd.search.utils.JdbcUtil;
import com.rmd.search.utils.SolrUtil;

public class Query {
	private static HashMap<Integer, Category> mapCategory = new HashMap<Integer, Category>();
	
	public static void init() throws Exception {
		if(mapCategory.size()>0){
			return;
		}
		String sql="select id,categoryname name,parentid pid from t_goods_category";
		Connection conn = JdbcUtil.getConnection();
		List<HashMap> listMap = JdbcUtil.queryList(sql, conn, true);
		System.out.println(listMap.size());
		for (HashMap map : listMap) {
			Iterator iter = map.entrySet().iterator();
			Class c=Category.class;
			Category cat=new Category();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				Field f=c.getDeclaredField((String) key);
				f.setAccessible(true);
				f.set(cat, val);
			}
			mapCategory.put(cat.getId(), cat);
		}
	}
	@Test
	public void test(){
		
	}
	@Test
	public void search() throws Exception {
		HttpSolrServer core=SolrUtil.getServer();
		SolrQuery query = new SolrQuery();
//        query.setQuery("*:*");
		query.setQuery("name:奥美康");
        query.setStart(0); // query的开始行数(分页使用)
        query.setRows(100); // query的返回行数(分页使用)
        query.setFacet(true); // 设置使用facet
        query.setFacetMinCount(1); // 设置facet最少的统计数量
        query.setFacetLimit(100); // facet结果的返回行数
        query.addFacetField("threeCategoryId","twoCategoryId","oneCategoryId", "brandId"); // facet的字段
        query.setFacetSort(FacetParams.FACET_SORT_COUNT);
        query.addFilterQuery("twoCategoryId:178");
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
	
	public static List<Goods> queryCategory1(String catId) throws Exception {
		HttpSolrServer core=SolrUtil.getServer();
		SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setStart(0); // query的开始行数(分页使用)
        query.setRows(1000); // query的返回行数(分页使用)
        query.setFacet(true); // 设置使用facet
        query.setFacetMinCount(1); // 设置facet最少的统计数量
        query.setFacetLimit(100); // facet结果的返回行数
        query.addFacetField("threeCategoryId","twoCategoryId","oneCategoryId", "brandId"); // facet的字段
        query.setFacetSort(FacetParams.FACET_SORT_COUNT);
        query.addFilterQuery("oneCategoryId:"+catId);
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
        return items_rep;
	}
	
	public static SearchList<Goods> query(String catId1,String catId2,String catId3,String sortBy) throws Exception {
		HttpSolrServer core=SolrUtil.getServer();
		SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setStart(0); // query的开始行数(分页使用)
        query.setRows(1000); // query的返回行数(分页使用)
        query.setFacet(true); // 设置使用facet
        query.setFacetMinCount(1); // 设置facet最少的统计数量
        query.setFacetLimit(100); // facet结果的返回行数
        query.addFacetField("threeCategoryId","twoCategoryId","oneCategoryId", "brandId"); // facet的字段
        query.setFacetSort(FacetParams.FACET_SORT_COUNT);
        if(StringUtils.isNotEmpty(catId3)){
        	query.addFilterQuery("threeCategoryId:"+catId3);
        }else if(StringUtils.isNotEmpty(catId2)){
        	query.addFilterQuery("twoCategoryId:"+catId2);
        }else if(StringUtils.isNotEmpty(catId1)){
        	query.addFilterQuery("oneCategoryId:"+catId1);
        }
        if(StringUtils.isNotEmpty(sortBy)){
        	query.addSort(new SortClause(sortBy, ORDER.desc)); // 排序
        }else{
        	query.addSort(new SortClause("id", ORDER.asc)); // 排序
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
            }
            
        }
        SearchList<Goods> searchList=new SearchList<>();
        searchList.setData(items_rep);
        searchList.setGroups(groupList);
        return searchList;
	}

}
