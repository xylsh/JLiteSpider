package Test.Douban;

import core.Spider;
import extension.DefaultDownloader;
import extension.PrintSaver;

public class DoubanMain {
	private static final String AGENT= "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) "
			+ "AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31";
	public static void main(String[] args) {
		Spider.create().setUrlList(new DoubanUrlList())
					   .setDownloader(new DefaultDownloader()
							   .setThreadPoolSize(1)
							   .setUserAgent(AGENT))
					   .setProcessor(new DoubanProcessor())
					   .setSaver(new PrintSaver())
					   .begin();
	}
}
