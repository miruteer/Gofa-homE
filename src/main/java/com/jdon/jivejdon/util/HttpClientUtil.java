package com.jdon.jivejdon.util;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpClientUtil {
	static DefaultHttpClient httpClient = new DefaultHttpClient();

	public static String get(String url, String encoding) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		HttpResponse res = httpClient.execute(httpGet);
		return getContent(res, encoding);
	}

	public static String get(String url, String encoding, DefaultHttpClient client) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		HttpResponse res = client.execute(httpGet);
		return getContent(res, encoding);
	}

	public static String post(String url, StringEntity se, String host, String referer, String encoding) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(se);
		httpPost.setHeader("Host", host);
		httpPost.setHeader("Referer", referer);
		httpPost.setHeader("Accept", "*/*");
		httpPost.setHeader("Accept-Language", "zh-cn");
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.setHeader("UA-CPU", "x86");
		httpPost.setHeader("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Mozilla/4.0 