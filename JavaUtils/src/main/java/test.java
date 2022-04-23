import javax.sound.midi.Soundbank;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/4/18
 */
public class test {
    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        Integer integer = integers.get(0);
        integer=2;
        System.out.println(integers);

        BigDecimal a = new BigDecimal(1.1);
        BigDecimal b = new BigDecimal(2);
        BigDecimal c = new BigDecimal(3);
        BigDecimal add = a.multiply(b).add(c);

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(3,3);
        map.put(1,1);
        map.put(2,2);
        map.put(1,1);
        map.put(4,4);
        map.put(5,5);
        for (Map.Entry<Integer, Integer> integerIntegerEntry : map.entrySet()) {
            System.out.println(integerIntegerEntry.getKey());
        }

        /**
         * DecimalFormat的作用，不实用的情况下仍然会存在丢死精度的问题
         */
        DecimalFormat format=new DecimalFormat("0.00000");
        BigDecimal d1=new BigDecimal(0.001F);
        System.out.println(d1.doubleValue());
        BigDecimal d2=new BigDecimal(format.format(0.001F));
        System.out.println(d2.doubleValue());


    }
}
