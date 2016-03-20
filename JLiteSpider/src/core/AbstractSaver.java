package core;

import java.util.Iterator;

/**
 * 存储爬取处理后的信息
 */
public abstract class AbstractSaver<T> implements Saver<T> {

    @Override
    public final void save(Iterator<T> entryIterator) {
        beforeSave(entryIterator);
        doSave(entryIterator);
        postSave(entryIterator);
    }

    protected void beforeSave(Iterator<T> entryIterator) {
        //nothing to do
    }

    protected void postSave(Iterator<T> entryIterator) {
        //nothing to do
    }

    protected abstract void doSave(Iterator<T> entryIterator);
}
