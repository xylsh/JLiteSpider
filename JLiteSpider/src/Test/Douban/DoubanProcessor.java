package Test.Douban;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import core.IteratorAdaptor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import core.Processor;
import core.Saver;
import util.IteratorUtils;

public class DoubanProcessor implements Processor<String> {

    @SuppressWarnings("unchecked")
    public void process(final Iterator<String> pages, Saver<String> saver) {
        Objects.requireNonNull(pages);
        Objects.requireNonNull(saver);

        saver.save(IteratorAdaptor.<String>create()
                        .setHasNext(() -> pages.hasNext())
                        .setCheck(() -> IteratorUtils.isEmpty(pages))
                        .setNext(() -> processCore(pages.next()))
        );
    }

    private String processCore(String content) {
        Document doc = Jsoup.parse(content);
        Element ele = doc.body();
        Elements es = ele.select("div#wrapper").select("div#content")
                .select("div.clearfix").select("div.article")
                .select("div.movie-list").select("dl");

        //单个页面产生的输出一般不会超级大
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < es.size(); i++) {
            result.append("href->");
            result.append(es.get(i).select("dt").select("a").attr("href"));
            result.append("\ntitle->");
            result.append(es.get(i).select("dd").select("a").text());
            result.append("\n");
        }
        return result.toString();
    }

}
