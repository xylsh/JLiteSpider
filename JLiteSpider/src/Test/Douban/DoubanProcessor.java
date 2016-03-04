package Test.Douban;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import core.Processor;
import core.Saver;

public class DoubanProcessor implements Processor {

	public void process(List<String> pages, Saver saver) {
		// TODO Auto-generated method stub
		for (String each : pages) {
			Document doc = Jsoup.parse(each);
			Element ele = doc.body();
			Elements es = ele.select("div#wrapper").select("div#content")
					.select("div.clearfix").select("div.article")
					.select("div.movie-list").select("dl");
			for (int i = 0; i < es.size(); i++) {
				saver.save("href", es.get(i).select("dt").select("a").attr("href"));
				saver.save("title", es.get(i).select("dd").select("a").text());
			}
		}
	}

}
