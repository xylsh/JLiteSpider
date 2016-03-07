package core;

import java.util.List;
/**
 * author : Yixin Luo
 * 2016/3/4
 * 对于要抓取的url链表的接口
 * **/
public interface UrlList<T> {
	/**
	 * 返回你想要抓取的url链表
	 * **/
	public List<T> returnUrlList();
}
