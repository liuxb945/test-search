package com.rmd.search.index;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import com.rmd.search.utils.JdbcUtil;
import com.rmd.search.utils.SolrUtil;
import com.rmd.search.model.Goods;


public class Index {
	public static final Logger logger =Logger.getLogger(Index.class);
	
	@Test
	public void load() throws Exception{
		String sql="select g.id,g.code,g.indbtime,g.modifytime,g.name,g.subname,gb.id brandId,gb.brandname brandName,gc.categoryname threeCategoryName,gc.id threeCategoryId,gc1.categoryname twoCategoryName,gc1.id twoCategoryId,gc2.categoryname oneCategoryName,gc2.id oneCategoryId from t_goods_base g left join t_goods_category gc on g.categoryid=gc.id left join t_goods_brand gb on g.brandid=gb.id left join t_goods_category gc1 on gc.parentid=gc1.id left join  t_goods_category gc2 on gc1.parentid=gc2.id ";
		Connection conn = JdbcUtil.getConnection();
		List<HashMap> listmap = JdbcUtil.queryList(sql, conn, true);
		System.out.println(listmap.size());
		addDocs(listmap);
	}
	
	
	
	public void MapIndex(List<HashMap> listMap) {
		for (HashMap map : listMap) {
			Iterator iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				System.out.println("key:"+key+",value:"+val);
			}
		}
	}
	
	public void addDocs(List<HashMap> listMap) throws Exception {
		HttpSolrServer server=SolrUtil.getServer();
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		for (HashMap map : listMap) {
			Iterator iter = map.entrySet().iterator();
			SolrInputDocument doc = new SolrInputDocument();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String)entry.getKey();
				Object val = entry.getValue();
				doc.addField(key, val);
			}
			docs.add(doc);
		}
		try {

			UpdateResponse response = server.add(docs);
			server.commit();

			logger.info("########## Query Time :" + response.getQTime());
			logger.info("########## Elapsed Time :" + response.getElapsedTime());
			logger.info("########## Status :" + response.getStatus());

		} catch (SolrServerException | IOException e) {
			logger.error("", e);
		}
	}
	
	public void addDoc(Goods goods) throws Exception {
		HttpSolrServer server=SolrUtil.getServer();
		SolrInputDocument doc = new SolrInputDocument();
		Class c = goods.getClass();  
		Field[] fs = c.getDeclaredFields();
		for(Field f:fs){
			f.setAccessible(true); //设置些属性是可以访问的
			Object val = f.get(goods);//得到此属性的值
			String type = f.getType().toString();//得到此属性的类型
			if(!f.getName().equals("serialVersionUID")&&!f.getName().equals("propMap")){
				doc.addField(f.getName(), val);
			}
		}
		try {

			UpdateResponse response = server.add(doc);
			server.commit();
			logger.info("########## Query Time :" + response.getQTime());
			logger.info("########## Elapsed Time :" + response.getElapsedTime());
			logger.info("########## Status :" + response.getStatus());

		} catch (SolrServerException | IOException e) {
			logger.error("", e);
		}
	}

}
