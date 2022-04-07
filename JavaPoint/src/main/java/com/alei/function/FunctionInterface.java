package com.alei.function;


import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @Author LeiLiMin
 * @Description: Jdk8: 函数式接口
 * @date: 2022/1/16
 */
@FunctionalInterface
public interface FunctionInterface {
    /**
     * what: 如果这个接口只有一个抽象方法，那么该接口就是一个函数式接口
     *
     * @FunctionalInterface: 如果在接口上定义了该注解，那么编译器会按照函数式接口的要求来定义该接口
     * 如果接口没有定义该注解，但是该接口仍然符合函数式接口的定义，那么编译器仍然认为这是一个函数式接口
     */
    void test();
}

class testClass {
    /**
     * Java8规范中的四大函数式接口
     */
    public static void main(String[] args) {
        // consumer接口
        consumer(1, m -> System.out.println("consumer accept: " + m));

        // supplier接口
        supplier(5, () -> {
            int supplierGet = ThreadLocalRandom.current().nextInt(10);
            return supplierGet;
        });

        // function接口
        function("111", m -> Integer.parseInt(m));

        // predicate接口
        predicate("11s1", m -> {
            try {
                Integer.parseInt(m);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    /**
     * 消费形接口: 有参数，无返回值
     */
    public static void consumer(Integer arg, Consumer<Integer> consumer) {
        // accept: 使用传入的arg进行consumer定义的操作
        consumer.accept(arg);
        // andThen: 对于同一个arg，先执行consumer的操作，再执行andThen入参中的consumer
        consumer.andThen(m -> System.out.println("andThen")).accept(arg);
    }

    /**
     * 供给形接口: 无参数,有返回值
     */
    public static void supplier(int arg, Supplier<Integer> supplier) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < arg; i++) {
            list.add(supplier.get());
        }
        for (Integer element : list) {
            System.out.println("supplier get: " + element);
        }
    }

    /**
     * 函数形接口: 有参数，有返回值
     */
    public static void function(String arg, Function<String, Integer> function) {
        System.out.println("function apply: " + function.apply(arg));

        /**
         * compose: 先执行f2，f2的返回值作为f1的arg继续执行
         *          细节就是这个Function的arg和return要是同一种类型
         */
        Function<Integer,Integer> f1=i->Integer.parseInt("1")*2;
        Function<Integer,Integer> f2=i->Integer.parseInt("1")+1;
        System.out.println(f1.compose(f2).apply(1));

        // andThen: 先执行f1，f1的返回值作为f2的arg继续执行
        f1.andThen(f2).apply(1);
    }

    /**
     * Predicate
     * 断言形接口: 有参数，返回Boolean
     */
    public static void predicate(String arg, Predicate<String> predicate) {
        System.out.println("predicate test: " + predicate.test(arg));

    }
}
