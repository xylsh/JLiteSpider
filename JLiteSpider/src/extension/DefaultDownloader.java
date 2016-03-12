package extension;

import java.util.Iterator;
import java.util.List;

import core.Downloader;
/**
 * author : Yixin Luo
 * 2016/3/3
 * 
 * 下载器，其中download函数应当返回获取到的html页面字符串的链表
 * 
 * **/
public class DefaultDownloader implements Downloader<String, String> {
	/*user agent*/
	private String agent = null;
	/*设置cookie*/
	private String cookie = null;
	/*传输超时*/
	private int timeout = 1000;
	/*设置代理*/
	private String proxy = null;
	/*线程池的线程数目*/
	private int threadPoolSize = 3;
	
	/**
	 * 设置下载传输参数
	 * **/
	public DefaultDownloader setUserAgent(String s) {
		this.agent = s;
		return this;
	}
	public DefaultDownloader setCookie(String c) {
		this.cookie = c;
		return this;
	}
	public DefaultDownloader setTimeout(int t) {
		this.timeout = t;
		return this;
	}
	public DefaultDownloader setProxy(String p) {
		this.proxy = p;
		return this;
	}
	public DefaultDownloader setThreadPoolSize(int size) {
		this.threadPoolSize = size;
		return this;
	}
	/**
	 * 使用UrlList对象中的url，开始下载
	 * **/
	public Iterator<String> download(Iterator<String> urls) {
		Network nw = Network.create().setUrlIterator(urls);
		if (this.agent != null)
			nw.setUserAgent(this.agent);
		if (this.cookie != null)
			nw.setCookie(this.cookie);
		if (this.proxy != null)
			nw.setProxy(this.proxy);
		
		return nw.setTimeout(this.timeout)
				.setThreadPoolSize(this.threadPoolSize)
				.begin()
				.getResults();
	}
}
