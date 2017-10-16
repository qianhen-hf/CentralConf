package com.huang.centralconf.client.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于httpcomponents的实现类
 */
public class HttpClientService {

	private static final int MAX_TOTAL_CONNECTIONS = 800;

	public final static int CONNECT_TIMEOUT = 5000;

	public final static int READ_TIMEOUT = 5000;

	public final static int MAX_ROUTE_CONNECTIONS = 50;

	private CloseableHttpClient httpClient;

	private RequestConfig defaultRequestConfig;

	private static Logger logger = LoggerFactory.getLogger(HttpClientService.class);

	public HttpClientService() {
		ConnectionConfig connectionConfig = ConnectionConfig.custom().setCharset(Consts.UTF_8).build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setDefaultConnectionConfig(connectionConfig);
		cm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
		cm.setMaxTotal(MAX_TOTAL_CONNECTIONS);
		defaultRequestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000)
				.setConnectionRequestTimeout(5000).build();
		httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(defaultRequestConfig)
				.build();
	}

	public String httpGet(String url, Map<String, String> urlparams) {
		return httpGet(url, urlparams, "UTF-8");
	}

	public String httpGet(String url) {
		return httpGet(url, null, "UTF-8");
	}

	public String httpGet(String url, Map<String, String> urlparams, String charset) {
		HttpGet httpGet = null;
		CloseableHttpResponse response = null;
		String content = null;
		try {
			URIBuilder builder = new URIBuilder(url);
			if (urlparams != null && urlparams.size() > 0) {
				for (Entry<String, String> entry : urlparams.entrySet()) {
					builder.addParameter(entry.getKey(), entry.getValue());
				}
			}
			httpGet = new HttpGet(builder.build());
			long start = System.currentTimeMillis();
			response = this.httpClient.execute(httpGet);
			logger.info("execute get request:[{}],cost [{}] ms", url,
					String.valueOf(System.currentTimeMillis() - start));
			HttpEntity entity = response.getEntity();
			content = EntityUtils.toString(entity, charset);
		} catch (Exception e) {
			logger.error("access {},error {} ", url, e);
			httpGet.abort();
			ExceptionUtils.throwException(e);
		} finally {
			try {
				if (response != null)
					response.close();
			} catch (IOException e) {
				httpGet.abort();
				logger.error("access {},error {} ", url, e);
			}
		}
		return content;

	}

	public String httpPost(String url) {
		return httpPost(url, null, null, "UTF-8");
	}

	public String httpPost(String url, Map<String, String> urlparams) {
		return httpPost(url, urlparams, null, "UTF-8");
	}

	public String httpPost(String url, Map<String, String> urlparams, Map<String, String> bodyParams) {
		return httpPost(url, urlparams, bodyParams, "UTF-8");
	}

	public String httpPost(String url, Map<String, String> urlparams, Map<String, String> bodyParams, String charset) {
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		String content = null;
		try {
			URIBuilder builder = new URIBuilder(url);
			if (urlparams != null && urlparams.size() > 0) {
				for (Entry<String, String> entry : urlparams.entrySet()) {
					builder.addParameter(entry.getKey(), entry.getValue());
				}
			}
			httpPost = new HttpPost(builder.build());

			if (bodyParams != null && bodyParams.size() > 0) {
				List<NameValuePair> bodyEntity = new ArrayList<>(bodyParams.size());
				for (Entry<String, String> entry : bodyParams.entrySet()) {
					bodyEntity.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(bodyEntity, charset));
			}

			long start = System.currentTimeMillis();
			response = this.httpClient.execute(httpPost);
			logger.info("execute get request:[{}],cost [{}] ms", url,
					String.valueOf(System.currentTimeMillis() - start));
			HttpEntity entity = response.getEntity();
			content = EntityUtils.toString(entity, charset);
		} catch (Exception e) {
			logger.error("access {},error. ", url, e);
			httpPost.abort();
			ExceptionUtils.throwException(e);
		} finally {
			try {
				if (response != null)
					response.close();
			} catch (IOException e) {
				httpPost.abort();
				logger.error("close {},error. ", url, e);
			}
		}
		return content;

	}

	public String httpPostJson(String url, String jsonBody) {
		return httpPostJson(url, null, jsonBody, "UTF-8");
	}

	public String httpPostJson(String url, Map<String, String> urlparams, String jsonBody) {
		return httpPostJson(url, urlparams, jsonBody, "UTF-8");
	}

	private String httpPostJson(String url, Map<String, String> urlparams, String jsonBody, String charset) {
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		String content = null;
		try {
			URIBuilder builder = new URIBuilder(url);
			if (urlparams != null && urlparams.size() > 0) {
				for (Entry<String, String> entry : urlparams.entrySet()) {
					builder.addParameter(entry.getKey(), entry.getValue());
				}
			}
			httpPost = new HttpPost(builder.build());

			if (StringUtils.isNotBlank(jsonBody)) {
				httpPost.setEntity(new StringEntity(jsonBody, charset));
			}

			long start = System.currentTimeMillis();
			response = this.httpClient.execute(httpPost);
			logger.info("execute get request:[{}],cost [{}] ms", url,
					String.valueOf(System.currentTimeMillis() - start));
			HttpEntity entity = response.getEntity();
			content = EntityUtils.toString(entity, charset);
		} catch (Exception e) {
			logger.error("access {},error {} ", url, e);
			httpPost.abort();
		} finally {
			try {
				if (response != null)
					response.close();
			} catch (IOException e) {
				httpPost.abort();
				logger.error("access {},error {} ", url, e);
			}
		}
		return content;

	}

	public <T> T httpGet(String url, Map<String, String> urlparams, HttpResponseParser<T> parser) {
		return httpGet(url, urlparams, "UTF-8", parser);
	}

	public <T> T httpGet(String url, HttpResponseParser<T> parser) {
		return httpGet(url, null, "UTF-8", parser);
	}

	private <T> T httpGet(String url, Map<String, String> urlparams, String charset, HttpResponseParser<T> parser) {
		String content = this.httpGet(url, urlparams, charset);
		return parser.parse(content);
	}

	public <T> T httpPost(String url, HttpResponseParser<T> parser) {
		return httpPost(url, null, null, "utf-8", parser);
	}

	public <T> T httpPost(String url, Map<String, String> urlparams, HttpResponseParser<T> parser) {
		return httpPost(url, urlparams, null, "utf-8", parser);
	}

	private <T> T httpPost(String url, Map<String, String> urlparams, Map<String, String> bodyParams, String charset,
			HttpResponseParser<T> parser) {
		String content = this.httpPost(url, urlparams, bodyParams, charset);
		return parser.parse(content);
	}

	public <T> T httpPostJson(String url, String jsonBody, HttpResponseParser<T> parser) {
		return httpPostJson(url, null, jsonBody, parser);
	}

	public <T> T httpPostJson(String url, Map<String, String> urlparams, String jsonBody,
			HttpResponseParser<T> parser) {
		String content = this.httpPostJson(url, urlparams, jsonBody, "UTF-8");
		return parser.parse(content);
	}

}
