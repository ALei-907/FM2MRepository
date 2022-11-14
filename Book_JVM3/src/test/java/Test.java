import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.Stack;

/**
 * @author LeiLiMin
 * @date: 2022/11/9
 */
public class Test {
    static String bracketLeftStr = "(";

    public static void main(String[] args) {
        String expression = "(1+2)+(2+1)";
        // System.out.println(expression.charAt(0));
        int bracketLeft = 0;
        int index = 0;
        while (index < expression.length()) {
            int preValue = -1;
            switch (expression.charAt(index)) {
                case ' ':
                    break;
                case '(':
                    bracketLeft++;
                    break;
            }
            index++;
        }
    }
}

