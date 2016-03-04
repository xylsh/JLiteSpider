package Main;

import java.util.List;
/**
 * author : Yixin Luo
 * 2016/3/4
 * 对于要抓取的url链表的一个抽象
 * **/
public abstract class UrlList {
	protected List<String> urlList;

	public UrlList(List<String> ul) {
		this.urlList = ul;
	}
	public UrlList() {
		// TODO Auto-generated constructor stub
	}
	public List<String> getUrlList() {
		return this.urlList;
	}
}
