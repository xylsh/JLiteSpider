package extension;

import java.util.List;

import core.UrlList;
/**
 * author : Yixin Luo
 * 2016/3/5
 * 
 * 简单UrlList的实现，用于接收外部直接输入的url链表
 * **/
public class DefaultUrlList implements UrlList {
	private List<String> urlList;
	
	public DefaultUrlList(List<String> u) {
		this.urlList = u;
	}
	
	public List<String> returnUrlList() {
		// TODO Auto-generated method stub
		return this.urlList;
	}

}
