import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.Stack;

/**
 * @author LeiLiMin
 * @date: 2022/11/9
 */
public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        // 全限定名称
        Class<?> test = Class.forName("com.alei.t01.T01");
        System.out.println(test.getName());
    }
}

