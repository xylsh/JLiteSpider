package extension;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

import core.AbstractSaver;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * author: Yixin Luo
 * 2016/3/3
 * <p>
 * 简单的对请求的url操作进行封装，
 * 返回对应的html字符串
 **/

public class Network {
    private Iterator<String> urlIterator;
    private Iterator<String> results;
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
     */
//	public Network setUrl(String url) {
    //todo:通过适配器转化成iterator
//		this.url = url;
//		return this;
//	}
//	public Network setUrlList(List<String> urls) {
    //todo:通过适配器转化成iterator
//		this.urls = urls;
//		return this;
//	}
    public Network setUrlIterator(Iterator<String> urlIterator) {
        this.urlIterator = urlIterator;
        return this;
    }

    /**
     * 设置下载传输参数
     **/
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
     **/
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 开始
     **/
    public Network begin() {
        Objects.requireNonNull(urlIterator);

        final ExecutorService pool = Executors.newFixedThreadPool(this.threadPoolSize);

        results = new Iterator<String>() {
            public boolean hasNext() {
                return urlIterator.hasNext();
            }

            public String next() {
                check();

                final String currUrl = urlIterator.next();
                Future<String> future = pool.submit(() -> downloader(currUrl));

                try {
                    return future.get();
                } catch (Exception e) {
                    e.printStackTrace();
                    return "<Error:" + e.getMessage() + ">";
                } finally {
                    if( !urlIterator.hasNext() ){
                        pool.shutdown();
                    }
                }
            }

            private void check() {
                if( urlIterator == null || !urlIterator.hasNext() ){
                    throw new NoSuchElementException();
                }
            }
        };

        return this;
    }

    public Iterator<String> getResults() {
        return results;
    }

    /*test unit*/
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        List<String> res = new ArrayList<String>();
        list.add("http://so.tv.sohu.com/list_p1100_p2_p3_p4_p5_p6_p73_p8_p91_p102_p11_p125_p13.html");
        list.add("http://so.tv.sohu.com/list_p1100_p2_p3_p4_p5_p6_p73_p8_p91_p103_p11_p125_p13.html");
        list.add("http://so.tv.sohu.com/list_p1100_p2_p3_p4_p5_p6_p73_p8_p91_p104_p11_p125_p13.html");
        list.add("http://so.tv.sohu.com/list_p1100_p2_p3_p4_p5_p6_p73_p8_p91_p105_p11_p125_p13.html");
//		res = Network.create().setUrlList(list)
//		.begin().toStringList();
//		System.out.println(res.get(0));
    }

}

