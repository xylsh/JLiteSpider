package core;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * 迭代器适配器
 */
public class IteratorAdaptor<T> implements Iterator<T> {

    private Supplier<Boolean> hasNextSupplier;
    private Supplier<T> nextSupplier;
    private Supplier<Boolean> checkSupplier;

    @Override
    public boolean hasNext() {
        return hasNextSupplier.get();
    }

    @Override
    public T next() {
        check();
        return nextSupplier.get();
    }

    public void check() {
        if (checkSupplier.get()) {
            throw new NoSuchElementException();
        }
    }

    public IteratorAdaptor<T> setHasNext(Supplier<Boolean> hasNextSupplier) {
        this.hasNextSupplier = hasNextSupplier;
        return this;
    }

    public IteratorAdaptor<T> setNext(Supplier<T> nextSupplier) {
        this.nextSupplier = nextSupplier;
        return this;
    }

    public IteratorAdaptor<T> setCheck(Supplier<Boolean> checkSupplier) {
        this.checkSupplier = checkSupplier;
        return this;
    }

    public static <T> IteratorAdaptor create() {
        return new IteratorAdaptor<T>();
    }
}
