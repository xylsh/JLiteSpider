package extension;

import core.AbstractSaver;
import core.Saver;

import java.util.Iterator;
import java.util.Objects;

/**
 * author : Yixin Luo
 * 2016/3/5
 * <p>
 * 对保存接口的实现，将结果打印出来
 **/
//public class PrintSaver implements Saver<String> {
public class PrintSaver extends AbstractSaver<String> {

    public void doSave(Iterator<String> entryIterator) {
        Objects.requireNonNull(entryIterator);

        while (entryIterator.hasNext()) {
            System.out.println(entryIterator.next());
        }
    }
}
