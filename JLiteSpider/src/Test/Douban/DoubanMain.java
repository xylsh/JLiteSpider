package Test.Douban;

import core.Spider;
import extension.DefaultDownloader;
import extension.PrintSaver;

public class DoubanMain {
	public static void main(String[] args) {
		Spider.create().setUrlList(new DoubanUrlList())
					   .setDownloader(new DefaultDownloader())
					   .setProcessor(new DoubanProcessor())
					   .setSaver(new PrintSaver())
					   .begin();
	}
}
