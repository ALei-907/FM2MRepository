package com.alei.deepcopy;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/4/9
 */
public class DeepCopyUtil {
    /**
     * 性能比较：
     * 	反射			140
     * 	序列化		341
     * 	拆箱/装箱	86
     * @param args
     */
    public static void main(String[] args) {
        List<List<Integer>> test = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            List<Integer> subList = new ArrayList<>();
            for (int j = 0; j < 1000; j++) {
                subList.add(i + j );
            }
            test.add(subList);
        }
        // Reflect
        long begin = System.currentTimeMillis();
        Object o1 = deepCopyDigitListByClassReflect(test);
        long end = System.currentTimeMillis();
        System.out.println("反射\t"+(end - begin));

        // Gson
        begin = System.currentTimeMillis();
        String val = new Gson().toJson(test);
        Type type = new TypeToken<List<List<Integer>>>() {}.getType();
        Object o = new Gson().fromJson(val, type);
        end = System.currentTimeMillis();
        System.out.println("序列化\t"+(end - begin));

        // package unPackage
        begin = System.currentTimeMillis();
        Object o2 = deepCopyDigitListByClassWraps(test);
        end = System.currentTimeMillis();
        System.out.println("拆箱/装箱\t"+(end - begin));


        // Final result
        System.out.println(o1.toString().equals(o2.toString()));

    }

    /**
     * digit-List的deepCopy(反射)
     * {@link DeepCopyUtil#getClass0(Collection)}
     * {@link DeepCopyUtil#isDigitType0(Class)}
     * {@link DeepCopyUtil#getBasicConstructor0(Class)}
     * {@link DeepCopyUtil#processorOfReflect0(ArrayList, Object[], Constructor)}
     */
    public static Object deepCopyDigitListByClassReflect(Collection c) {
        Object[] a = c.toArray();
        if (a.length != 0) {
            // 获取最内层 类型
            Class type = getClass0(c);
            // 是否位基本数据类型包装类
            isDigitType0(type);
            // 获取对应的构造方法
            Constructor basicConstructor = getBasicConstructor0(type);
            // 深拷贝开始
            return processorOfReflect0(new ArrayList<>(), a, basicConstructor);
        }
        return c == null ? null : new ArrayList<>();
    }

    /**
     * deepCopy核心处理流程
     */
    public static Object processorOfReflect0(ArrayList<Object> result, Object[] a, Constructor constructor) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] instanceof List) {
                ArrayList<Object> subElement = new ArrayList<>();
                result.add(subElement);
                processorOfReflect0(subElement, ((List) a[i]).toArray(), constructor);
            } else {
                try {
                    result.add(constructor.newInstance(a[i]));
                } catch (Exception e) {
                    throw new RuntimeException("InstanceException");
                }
            }
        }
        return result;
    }


    /**
     *  数值List的deepCopy(拆箱/装箱)
     * {@link DeepCopyUtil#getClass0(Collection)}
     * {@link DeepCopyUtil#isDigitType0(Class)}
     * {@link DeepCopyUtil#processorOfClassWraps0(ArrayList, Object[])}
     */
    public static Object deepCopyDigitListByClassWraps(Collection c) {
        Object[] a = c.toArray();
        if (a.length != 0) {

            Class type = getClass0(c);

            isDigitType0(type);

            return processorOfClassWraps0(new ArrayList<>(), a);
        }
        return c == null ? null : new ArrayList<>();
    }



    /**
     * deepCopy核心处理流程
     */
    private static Object processorOfClassWraps0(ArrayList<Object> result, Object[] a) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] instanceof List) {
                ArrayList<Object> subElement = new ArrayList<>();
                result.add(subElement);
                processorOfClassWraps0(subElement, ((List) a[i]).toArray());
            } else {
                try {
                    if(a[i] instanceof Byte){
                        byte num=(byte) a[i];
                        result.add(num);
                    }
                    if(a[i] instanceof Short){
                        short num=(short) a[i];
                        result.add(num);
                    }
                    if(a[i] instanceof Long){
                        long num=(long) a[i];
                        result.add(num);
                    }
                    if(a[i] instanceof Double){
                        double num=(double) a[i];
                        result.add(num);
                    }
                    if(a[i] instanceof Float){
                        float num=(float) a[i];
                        result.add(num);
                    }
                    if(a[i] instanceof Integer){
                        int num=(int) a[i];
                        result.add(num);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }



    /**
     * 递归获取最内层的元素类型
     */
    private static Class getClass0(Collection c) {
        Object[] a = c.toArray();
        while (true) {
            if (a.length != 0 && (a[0] instanceof List)) {
                a = ((List) a[0]).toArray();
                continue;
            }
            return a[0].getClass();
        }
    }

    /**
     * 判断type是否为数值类型
     */
    private static void isDigitType0(Class type) {
        if (!(type == Short.class ||
                type == Integer.class ||
                type == Byte.class ||
                type == Long.class ||
                type == Double.class)) {
            throw new RuntimeException("[" + type + "] is not a basic type!");
        }
    }

    /**
     * 获取数值类型的构造方法
     * @param type
     * @return
     */
    private static Constructor getBasicConstructor0(Class type) {
        try {
            Constructor constructor = null;
            if (type == Byte.class) {
                constructor = type.getConstructor(byte.class);
            }
            if (type == Short.class) {
                constructor = type.getConstructor(short.class);
            }
            if (type == Integer.class) {
                constructor = type.getConstructor(int.class);
            }
            if (type == Long.class) {
                constructor = type.getConstructor(long.class);
            }
            if (type == Double.class) {
                constructor = type.getConstructor(double.class);
            }
            return constructor;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("NoSuchMethodException");
        }
    }
}
