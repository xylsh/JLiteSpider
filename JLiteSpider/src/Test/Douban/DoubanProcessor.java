package Test.Douban;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import core.Processor;
import core.Saver;

public class DoubanProcessor implements Processor<String> {

    public void process(final Iterator<String> pages, Saver<String> saver) {
        Objects.requireNonNull(pages);
        Objects.requireNonNull(saver);

        saver.save(new Iterator<String>() {
            public boolean hasNext() {
                return pages.hasNext();
            }

            public String next() {
                check();

                String content = pages.next();
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

            private void check() {
                if( pages == null || !pages.hasNext() ){
                    throw new NoSuchElementException();
                }
            }
        });
    }


}
