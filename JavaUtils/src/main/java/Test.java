import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Data
class TestA {
    private Integer a;
}

public class Test {
    private ThreadLocal<Map<String, Object>> tl = new ThreadLocal<>();

    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("random", new Random());
        map.put("this", this);
        tl.set(map);
    }

    public Integer value1 = 1;

    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(-5));


        // 【HmacSha256】
        // String stringToSign = "658569CB-B407-50DC-B205-CC87AD82A3D9" + "/n" + "Android";
        // Mac mac = Mac.getInstance("HmacSHA256");
        // mac.init(new SecretKeySpec("Cash_Fortune".getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        // byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        // System.out.println(new String(Base64.encodeBase64(signData)));

        // 【String to IOStream】
        // // String to InputStream
        // String test = "abc";
        // InputStream inputStream = new ByteArrayInputStream(test.getBytes());
        // byte[] byteArr = new byte[test.getBytes().length];
        // inputStream.read(byteArr, 0, test.getBytes().length);
        // String s = new String(byteArr, Charset.forName("UTF-8"));
        // System.out.println(s);
        // //
        // int[] ints = {1, 2, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010, 1011};
        // StringBuffer symbol = new StringBuffer();
        // StringBuffer weight = new StringBuffer();
        //
        // for (int i = 0; i < 30; i++) {
        //     Random random = new Random();
        //     int i1 = random.nextInt(ints.length);
        //     symbol.append(ints[i1]).append(",");
        //     weight.append(1).append(",");
        // }
        // System.out.println(symbol);
        // System.out.println(weight);
        //
        //
        // List<Integer> ids = new ArrayList<>();
        // ids.add(1);
        // ids.add(2);
        // ids.add(3);
        // for (Integer id : ids) {
        //     if (id == 1) {
        //         ids.set(0, 0);
        //
        //     }
        // }
        // System.out.println(ids);
    }
}

