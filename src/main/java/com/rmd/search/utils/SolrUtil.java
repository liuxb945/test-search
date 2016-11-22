package com.rmd.search.utils;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;

public class SolrUtil {
	public static HttpSolrServer getServer(){
		String url = "http://localhost:8080/solr/rmdsearch";
        HttpSolrServer core = new HttpSolrServer(url);
        core.setMaxRetries(1);
        core.setConnectionTimeout(5000);
        core.setParser(new XMLResponseParser()); // binary parser is used by
                                                    // default
        core.setSoTimeout(1000); // socket read timeout
        core.setDefaultMaxConnectionsPerHost(100);
        core.setMaxTotalConnections(100);
        core.setFollowRedirects(false); // defaults to false
        core.setAllowCompression(true);
        return core;
	}

}
