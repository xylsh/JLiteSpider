package extension;

import java.util.List;

import core.Downloader;
/**
 * author : Yixin Luo
 * 2016/3/3
 * 
 * 下载器，其中download函数应当返回获取到的html页面字符串的链表
 * 
 * **/
public class DefaultDownloader implements Downloader {
	/**
	 * 使用UrlList对象中的url，开始下载
	 * **/
	public List<String> download(List<String> urlList) {
		return Network.create().setUrlList(urlList)
				.begin().toStringList();
	}
}
