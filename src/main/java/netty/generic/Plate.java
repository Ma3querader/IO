package netty.generic;

/**
 * @Author: panyusheng
 * @Date: 2021/4/21
 * @Version 1.0
 */
public class Plate<T> {
    private T item;

    public Plate(T t) {
        item = t;
    }

    public void set(T t) {
        item = t;
    }

    public T get() {
        return item;
    }
}
