package netty.generic;

/**
 * @Author: panyusheng
 * @Date: 2021/4/21
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        Plate<? extends Fruit> p = new Plate<Apple>(new Apple());
        Fruit fruit = p.get();
        System.out.println(fruit);
    }
}
