package extension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * author: Yixin Luo
 * 2016/3/3
 * 
 * 简单的对请求的url操作进行封装，
 * 返回对应的html字符串
 * 
 * **/

public class Network {
	/*请求的url或url链表*/
	private String url = "";
	private List<String> urls = new ArrayList<String>();
	/*返回的结果字符串或链表*/
	private String result = "";
	private List<String> results = new ArrayList<String>();
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
	
	public static Network create() {
		return new Network();
	}
	/**
	 * 设置要请求的url
	 * */
	public Network setUrl(String url) {
		this.url = url;
		return this;
	}
	public Network setUrlList(List<String> urls) {
		this.urls = urls;
		return this;
	}
	/**
	 * 设置下载传输参数
	 * **/
	public Network setUserAgent(String s) {
		this.agent = s;
		return this;
	}
	public Network setCookie(String c) {
		this.cookie = c;
		return this;
	}
	public Network setTimeout(int t) {
		this.timeout = t;
		return this;
	}
	public Network setProxy(String p) {
		this.proxy = p;
		return this;
	}
	public Network setThreadPoolSize(int size) {
		this.threadPoolSize = size;
		return this;
	}
	/**
	 * 下载，并返回string
	 * **/
	private String downloader(String url) {
		String res = "";
		try {
			Request rq = Request.Get(url).connectTimeout(this.timeout);
			if (this.agent != null)
				rq = rq.userAgent(this.agent);
			if (this.cookie != null)
				rq = rq.addHeader("Cookie", this.cookie);
			if (this.proxy != null)
				rq = rq.viaProxy(this.proxy);
			res = rq.execute().returnContent().asString();
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	/**
	 * 开始
	 * **/
	public Network begin() {
		//设置了要请求的url
		if (!this.url.equals("")) {
			this.result = downloader(this.url);
		} else if (!this.urls.isEmpty()) {
			//设置了要请求的url队列
			/*
			 * 这里使用定长为3的线程池
			 * */
			final Network tmp = this;
			ExecutorService pool = Executors.newFixedThreadPool(this.threadPoolSize);
			for (String each : urls) {
				final String tmpeach = each;
				pool.execute(new Runnable() {
					public void run() {
						// TODO Auto-generated method stub
						tmp.results.add(downloader(tmpeach));
					}
				});
			}
			pool.shutdown();
			while(!pool.isTerminated());
		} else {
			System.err.println("error : 未设置要请求的url");
		}
		return this;
	}
	/**
	 * 返回想要的结果
	 * **/
	public String toString() {
		return this.result;
	}
	public List<String> toStringList() {
		return this.results;
	}
	
	/*test unit*/
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		List<String> res = new ArrayList<String>();
		list.add("http://so.tv.sohu.com/list_p1100_p2_p3_p4_p5_p6_p73_p8_p91_p102_p11_p125_p13.html");
		list.add("http://so.tv.sohu.com/list_p1100_p2_p3_p4_p5_p6_p73_p8_p91_p103_p11_p125_p13.html");
		list.add("http://so.tv.sohu.com/list_p1100_p2_p3_p4_p5_p6_p73_p8_p91_p104_p11_p125_p13.html");
		list.add("http://so.tv.sohu.com/list_p1100_p2_p3_p4_p5_p6_p73_p8_p91_p105_p11_p125_p13.html");
		res = Network.create().setUrlList(list)
		.begin().toStringList();
		System.out.println(res.get(0));
	}
	
}

