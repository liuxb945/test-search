package com.rmd.search.index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rmd.search.dao.GoodsDao;
import com.rmd.search.dao.PropertyDao;
import com.rmd.search.model.Goods;
import com.rmd.search.model.PropertyItem;
import com.rmd.search.utils.SolrUtil;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class Index {
	public static final Logger logger =Logger.getLogger(Index.class);
	
	@Resource(name="goodsDao")
	private GoodsDao goodsDao;
	
	@Resource(name="propertyDao")
	private PropertyDao propertyDao;
	
	@Test
	public void indexGoods() throws SolrServerException, IOException{
		List<Goods> goodsList=goodsDao.loadGoods();
		for(Goods g:goodsList){
			System.out.println(g);
			List<PropertyItem> props=this.propertyDao.loadPropsByGoodsId(g.getId());
			String propValues="";
			for(PropertyItem item:props){
				String str=item.toPropString();
				//去重
				if (propValues.indexOf(str) < 0) {
					propValues += "," + item.toPropString();
				}
			}
			if(!propValues.equals("")){
				propValues=propValues.substring(1);
			}
			System.out.println(propValues);
			g.setPropVals(propValues);
		}
		if(goodsList!=null&&goodsList.size()>0){
			HttpSolrServer server=SolrUtil.getServer();
			server.deleteByQuery("*:*");
			UpdateResponse response = server.addBeans(goodsList);
			server.commit();
			logger.info("########## Query Time :" + response.getQTime());
			logger.info("########## Elapsed Time :" + response.getElapsedTime());
			logger.info("########## Status :" + response.getStatus());
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
	
}
