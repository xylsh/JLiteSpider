package Test.Douban;

import java.util.ArrayList;
import java.util.List;

import core.UrlList;

/**
 *  生成，要抓取的url链表
 * 
 * **/
public class DoubanUrlList implements UrlList{

	/**
	 *   notice: 在这个函数中，新建要创建的url链表并返回
	 * **/
	public List<String> returnUrlList() {
		// TODO Auto-generated method stub
		List<String> urlList = new ArrayList<String>();
		urlList.add("http://www.douban.com/tag/中国/movie");
		urlList.add("http://www.douban.com/tag/中国/movie?start=15");
		urlList.add("http://www.douban.com/tag/中国/movie?start=30");
		return urlList;
	}
	
}
