package com.alei.S003_stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author LeiLiMin
 * @Description: Stream流的使用
 * @date: 2022/2/18
 */
public class T03_Stream {
    /**
     * 对每一个元素进行筛选操作
     */
    public static void invokeMap() {
        // 1.筛选出User#age,所有元素组成一个List
        List<User> users = User.getUsers();
        List<Integer> ageList = users.stream().map(User::getAge).collect(Collectors.toList());
        System.out.println(ageList);
    }

    /**
     * 只能是过滤，决定的是是否留下，而不能改变流中元素的类型
     */
    public static void invokeFilter() {
        List<User> users = User.getUsers();
        // 将filter中返回结构为ture的元素保留下来，过滤为false的元素
        List<User> males = users.stream().filter(e -> e.getSex() == true).collect(Collectors.toList());

        /**
         * User(age=1, sex=true, name=Lewis),
         * User(age=3, sex=true, name=Mike)
         */
        System.out.println(males);
    }

    /**
     * 对集合中的元素进行分组
     */
    public static void invokeGroup() {
        List<User> users = User.getUsers();

        // 按照名称分组
        Map<String, List<User>> name = users.stream().collect(Collectors.groupingBy(User::getName));

        // 按照性别进行分区，分区的Key都为Boolean
        Map<Boolean, List<User>> malePartition = users.stream().collect(Collectors.partitioningBy(e -> e.getSex() == true));

        // 先按照性别分组，再按照年龄分组
        Map<Boolean, Map<Integer, List<User>>> maleAndAge = users.stream().collect(Collectors.groupingBy(User::getSex, Collectors.groupingBy(User::getAge)));

        // result

        /**
         * {Mike=[User(age=3, sex=true, name=Mike)],
         * Lewis=[User(age=1, sex=true, name=Lewis)],
         * Lucy=[User(age=2, sex=false, name=Lucy)],
         * Lily=[User(age=4, sex=false, name=Lily)]}
         */
        System.out.println(name);

        /**
         * 不满足条件:false=[User(age=2, sex=false, name=Lucy), User(age=4, sex=false, name=Lily)]
         *  满足条件:true=[User(age=1, sex=true, name=Lewis), User(age=3, sex=true, name=Mike)]}
         */
        System.out.println(malePartition);

        /**
         * {
         *  false={2=[User(age=2, sex=false, name=Lucy)],
         *         4=[User(age=4, sex=false, name=Lily)]},
         *  true={1=[User(age=1, sex=true, name=Lewis)],
         *        3=[User(age=3, sex=true, name=Mike)]}}
         */
        System.out.println(maleAndAge);
    }

    /**
     * 将集合中的每个元素通过分隔符拼接起来
     */
    public static void invokeJoin() {
        Long[] arr = {1L, 2L, 3L, 4L};
        String substring = StringUtils.join(arr, ",");
        System.out.println("StringUtils#join():" + substring);
        String collect = Arrays.stream(arr).map(e -> e.toString()).collect(Collectors.joining(","));
        System.out.println(collect);
    }


    /**
     * 对元素进行操作
     */
    public static void calculateSumOfInt() {
        List<Integer> weight = new ArrayList<>();
        weight.add(1);
        weight.add(3);
        weight.add(5);
        weight.add(7);

        int weightAccMax = weight.stream().mapToInt(Integer::intValue).sum();

        // 特别注意的是int[]如果直接转为list -> 结果: List<int[]>
        // 需要进行#mapToObj()
        int[] arr = new int[]{1, 2, 3, 4};
        String collect = Arrays.stream(arr).mapToObj(e -> String.valueOf(e)).collect(Collectors.joining(","));
    }

    /**
     * List<int[]>类型输出: 因为int[]在日志输出时为内存地址，所以需要用到这种方式来进行简便的日志输出
     */
    public static void basicArrInList() {
        int[] arr1 = new int[]{1, 2, 3, 4, 5};
        int[] arr2 = new int[]{6, 7, 8, 9, 10};
        int[] arr3 = new int[]{11, 12, 13, 14, 15};
        List<int[]> list = new ArrayList<>();
        list.add(arr1);
        list.add(arr2);
        list.add(arr3);

        System.out.println(list.stream().map(e ->
                Arrays.stream(e).mapToObj(i -> String.valueOf(i)).collect(Collectors.joining(","))
        ).collect(Collectors.toList()));
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class User {
    private Integer age;
    private Boolean sex;
    private String name;
    private Address add;

    public static List<User> getUsers() {
        List<User> list = new ArrayList<>();
        list.add(new User(1, true, "Lewis", new Address()));
        list.add(new User(2, false, "Lucy", new Address()));
        list.add(new User(3, true, "Mike", new Address()));
        list.add(new User(4, false, "Lily", new Address()));
        return list;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Address {
        private String province;
        private String city;
        private String county;
    }
}
