package core;

import java.util.List;

/**
 * author: Yixin Luo
 * 2016/3/4
 * 
 * 下载器的接口
 * **/
public interface Downloader {
	public List<String> download(List<String> urlList);
}
