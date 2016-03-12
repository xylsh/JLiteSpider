package core;

import java.util.Objects;

/**
 * author:Yixin Luo
 * 2016/3/3
 * 
 * 爬虫组装工厂
 * **/
public class Spider {
	private Downloader downloader;
	private Processor processor;
	private Saver saver;
//	private UrlList urlList;
	private UrlSource urlSource;
	
	public static Spider create() {
		return new Spider();
	}
	
	public Spider setUrlSource(UrlSource urlSource) {
		Objects.requireNonNull(urlSource);

		this.urlSource = urlSource;
		return this;
	}
	
	public Spider setDownloader(Downloader d) {
		this.downloader = d;
		return this;
	}
	
	public Spider setProcessor(Processor p) {
		this.processor = p;
		return this;
	}
	
	public Spider setSaver(Saver s) {
		this.saver = s;
		return this;
	}
	
	/*开始下载和解析*/
	public void begin() {
		processor.process(
				downloader.download(this.urlSource.iterator()),
				saver
		);
	}
}

