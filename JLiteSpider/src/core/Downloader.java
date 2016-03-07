package core;

import java.util.List;

/**
 * author: Yixin Luo
 * 2016/3/4
 * 
 * 下载器的接口
 * **/
public interface Downloader<D, R> {
	/**
	 * 这个函数将url链表中对应的网页下载，然后将结果保存到字符串链表中，返回
	 * **/
	public List<R> download(List<D> urlList);
}
