package edu.uci.ics.crawler4j.crawler;

import java.io.IOException;
import java.util.Date;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParamBean;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import edu.uci.ics.crawler4j.frontier.DocIDServer;
import edu.uci.ics.crawler4j.url.URLCanonicalizer;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * Copyright (C) 2010.
 * 
 * @author Yasser Ganjisaffar <yganjisa at uci dot edu>
 */

public final class PageFetcher {

	private static final Logger logger = Logger.getLogger(PageFetcher.class);

	private static ThreadSafeClientConnManager connectionManager;

	private static DefaultHttpClient httpclient;

	private static Object mutex = PageFetcher.class.toString() + "_MUTEX";

	private static int processedCount = 0;

	private static long startOfPeriod = 0;

	private static long lastFetchTime = 0;

	private static long politenessDelay = Configurations.getIntProperty(
			"fetcher.default_politeness_delay", 200);

	public static final int MAX_DOWNLOAD_SIZE = Configurations.getIntProperty(
			"fetcher.max_download_size", 1048576);

	private static final boolean show404Pages = Configurations
			.getBooleanProperty("logging.show_404_pages", true);

	public static long getPolitenessDelay() {
		return politenessDelay;
	}

	public static void setPolitenessDelay(long politenessDelay) {
		PageFetcher.politenessDelay = politenessDelay;
	}

	static {
		HttpParams params = new BasicHttpParams();
		HttpProtocolParamBean paramsBean = new HttpProtocolParamBean(params);
		paramsBean.setVersion(HttpVersion.HTTP_1_1);
		paramsBean.setContentCharset("UTF-8");
		paramsBean.setUseExpectContinue(false);

		params.setParameter("http.useragent", Configurations.getStringProperty(
				"fetcher.user_agent",
				"crawler4j (http://code.google.com/p/crawler4j/)"));

		params.setIntParameter("http.socket.timeout", Configurations
				.getIntProperty("fetcher.socket_timeout", 20000));

		params.setIntParameter("http.connection.timeout", Configurations
				.getIntProperty("fetcher.connection_timeout", 30000));

		params.setBooleanParameter("http.protocol.handle-redirects",
				Configurations.getBooleanProperty("fetcher.follow_redirects",
						true));

		ConnPerRouteBean connPerRouteBean = new ConnPerRouteBean();
		connPerRouteBean.setDefaultMaxPerRoute(Configurations.getIntProperty(
				"fetcher.max_connections_per_host", 100));
		ConnManagerParams.setMaxConnectionsPerRoute(params, connPerRouteBean);
		ConnManagerParams.setMaxTotalConnections(params, Configurations
				.getIntProperty("fetcher.max_total_connections", 100));

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));

		if (Configurations.getBooleanProperty("fetcher.crawl_https", false)) {
			schemeRegistry.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));
		}

		connectionManager = new ThreadSafeClientConnManager(params,
				schemeRegistry);
		logger.setLevel(Level.INFO);
		httpclient = new DefaultHttpClient(connectionManager, params);
	}

	public static void startConnectionMonitorThread() {
		new Thread(new IdleConnectionMonitorThread(connectionManager)).start();
	}

	public static int fetch(Page page) {
		String toFetchURL = page.getWebURL().getURL();
		HttpGet get = null;
		HttpEntity entity = null;
		try {
			get = new HttpGet(toFetchURL);
			synchronized (mutex) {
				long now = (new Date()).getTime();
				if (now - startOfPeriod > 10000) {
					logger.info("Number of pages fetched per second: "
							+ processedCount / ((now - startOfPeriod) / 1000));
					processedCount = 0;
					startOfPeriod = now;
				}
				processedCount++;

				if (now - lastFetchTime < politenessDelay) {
					Thread.sleep(politenessDelay - (now - lastFetchTime));
				}
				lastFetchTime = (new Date()).getTime();
			}
			HttpResponse response = httpclient.execute(get);
			entity = response.getEntity();

			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				if (response.getStatusLine().getStatusCode() != HttpStatus.SC_NOT_FOUND) {
					logger.info("Failed: "
							+ response.getStatusLine().toString()
							+ ", while fetching " + toFetchURL);
				} else if (show404Pages) {
					logger.info("Not Found: " + toFetchURL
							+ " (Link found in doc#: "
							+ page.getWebURL().getParentDocid() + ")");
				}
				return response.getStatusLine().getStatusCode();
			}

			String uri = get.getURI().toString();
			if (!uri.equals(toFetchURL)) {
				if (!URLCanonicalizer.getCanonicalURL(uri).equals(toFetchURL)) {
					int newdocid = DocIDServer.getDocID(uri);
					if (newdocid != -1) {
						if (newdocid > 0) {
							return PageFetchStatus.RedirectedPageIsSeen;
						}
						page.setWebURL(new WebURL(uri, -newdocid));
					}
				}
			}

			if (entity != null) {
				long size = entity.getContentLength();
				if (size == -1) {
					Header length = response.getLastHeader("Content-Length");
					if (length == null) {
						length = response.getLastHeader("Content-length");
					}
					if (length != null) {
						size = Integer.parseInt(length.getValue());
					} else {
						size = -1;
					}
				}
				if (size > MAX_DOWNLOAD_SIZE) {
					entity.consumeContent();
					return PageFetchStatus.PageTooBig;
				}

				boolean isBinary = false;

				Header type = entity.getContentType();
				if (type != null //alterado content type de image para application
						&& type.getValue().toLowerCase().contains("application")) {
					isBinary = true;
				}

				if (page.load(entity.getContent(), (int) size, isBinary)) {
					return PageFetchStatus.OK;
				} else {
					return PageFetchStatus.PageLoadError;
				}
			} else {
				get.abort();
			}
		} catch (IOException e) {
			logger.error("Fatal transport error: " + e.getMessage()
					+ " while fetching " + toFetchURL + " (link found in doc #"
					+ page.getWebURL().getParentDocid() + ")");
			return PageFetchStatus.FatalTransportError;
		} catch (IllegalStateException e) {
			// ignoring exceptions that occur because of not registering https
			// and other schemes
		} catch (Exception e) {
			if (e.getMessage() == null) {
				logger.error("Error while fetching "
						+ page.getWebURL().getURL());
			} else {
				logger.error(e.getMessage() + " while fetching "
						+ page.getWebURL().getURL());
			}
		} finally {
			try {
				if (entity != null) {
					entity.consumeContent();
				} else if (get != null) {
					get.abort();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return PageFetchStatus.UnknownError;
	}

	public static void setProxy(String proxyHost, int proxyPort) {
		HttpHost proxy = new HttpHost(proxyHost, proxyPort);
		httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
				proxy);
	}

	public static void setProxy(String proxyHost, int proxyPort,
			String username, String password) {
		httpclient.getCredentialsProvider().setCredentials(
				new AuthScope(proxyHost, proxyPort),
				new UsernamePasswordCredentials(username, password));
		setProxy(proxyHost, proxyPort);
	}

}
