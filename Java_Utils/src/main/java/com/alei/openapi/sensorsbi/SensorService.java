package com.alei.openapi.sensorsbi;


import com.alei.openapi.sensorsbi.utils.DefaultThreadFactory;
import com.alei.openapi.sensorsbi.utils.TraceUtil;
import com.sensorsdata.analytics.javasdk.ISensorsAnalytics;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.bean.EventRecord;
import com.sensorsdata.analytics.javasdk.consumer.ConcurrentLoggingConsumer;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author LeiLiMin
 * @Description: 神策埋点服务
 * @date: 2022/1/17
 */
@Slf4j
public class SensorService {
    // 日志文件：完全路径名称
    private String LOG_FILE_NAME;

    // 生产数据的顶级接口对象
    public static ISensorsAnalytics sa;

    // 线程服务
    private static ScheduledExecutorService scheduledExecutorService;

    // 系统环境：开发环境
    private static String ENV_DEV = "DEV";

    // 系统环境：生产环境
    private static String ENV_PRODUCT = "PRODUCT";

    // 系统环境
    private String env = System.getenv("ENV");

    // 系统变量中的log缓冲区大小
    private static final int LOG_BUFFER_SIZE = Integer.parseInt(StringUtils.isBlank(System.getenv("LOG_BUFFER_SIZE")) ? "0" : System.getenv("LOG_BUFFER_SIZE"));


    static {
        scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new DefaultThreadFactory("SensorsTask"));
    }

    public SensorService(String fileName) {
        // 1.创建文件名
        this.LOG_FILE_NAME = SensorsConstants.LOG_FILE_PATH + fileName;

        // 2.数据生产类
        if (Objects.isNull(sa)) {
            sa = new SensorsAnalytics(new ConcurrentLoggingConsumer(this.LOG_FILE_NAME, LOG_BUFFER_SIZE));
        }
        // 3.如果是生产环境，就创建一个定时任务去flush buffer
        if (ENV_PRODUCT.equals(env)) {
            /**
             * 生产环境: 使用 ConcurrentLoggingConsumer导入数据。
             *          支持多个进程写同一个目录（目录不能是 nas、nfs 类文件系统）
             */
            scheduledExecutorService.scheduleAtFixedRate(() -> {
                this.sa.flush();
            }, 1L, 5L, TimeUnit.MINUTES);
        }

        // 4.为JVM增加一个Hook，当Jvm退出时，让consumer及时flush
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                Optional.ofNullable(sa).ifPresent(sa -> sa.shutdown());
            }
        });
    }


    /**
     * 记录一个没有任何属性的事件
     *
     * @param distinctId 用户ID
     * @param eventName  事件名称
     * @param isLoginId  为true时distinctId是一个userId  false时是设备ID
     */
    public void track(String distinctId, String eventName, boolean isLoginId) {
        try {
            sa.track(distinctId, isLoginId, eventName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 记录一个拥有一个或多个属性的事件。属性取值可接受类型为{@link Number}, {@link String}, {@link Date}和
     * {@link List}，若属性包含 $time 字段，则它会覆盖事件的默认时间属性，该字段只接受{@link Date}类型
     *
     * @param distinctId 用户ID
     * @param event      事件名称--顶级
     * @param eventName  事件名称--次级
     * @param properties 事件的属性
     * @param isLoginId  为true时distinctId是一个userId  false时是设备ID
     */
    public void track(String distinctId, String event, String eventName, Map<String, Object> properties, boolean isLoginId) {
        properties.put(SensorsConstants.KEY_EVENT_NAME, eventName);
        properties = this.addTraceId(eventName, properties);
        try {
            // 1.设置distinctId，eventName，isLoginId
            EventRecord build = EventRecord
                    .builder()
                    .setDistinctId(distinctId)
                    .isLoginId(isLoginId)
                    .setEventName(event)
                    .addProperties(properties).build();
            // 2.记录用户浏览商品事件
            sa.track(build);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    /**
     * 存入TraceId
     *
     * @param properties
     * @return
     */
    private Map<String, Object> addTraceId(String eventName, Map<String, Object> properties) {
        String traceId = TraceUtil.readTraceId();
        if (StringUtils.isBlank(traceId)) {
            traceId = TraceUtil.createTraceId();
        }
        properties.put(SensorsConstants.TRACE_ID_SENSOR_KEY, traceId);
        return properties;
    }

    /**
     * 记录用户注册事件
     * 绑定distinctId与UserId，用于后期分析
     *
     * @param distinctId       新的用户ID
     * @param originDistinctId 旧的用户ID
     */
    public void trackSignUp(String distinctId, String originDistinctId) {
        try {
            sa.trackSignUp(distinctId, originDistinctId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    // 需要将三级目录封装二级目录
    public static void main(String[] args) throws InvalidArgumentException {
        SensorService sensorService = new SensorService("api of java");
        // sensorService.trackSignUp("2", "1");
        // HashMap<Object, Object> subMap = new HashMap<>();
        // subMap.put("event_name", "test");
        // Map<String, Object> map = new HashMap<>();
        // List<String> list = new ArrayList<>();
        // list.add("1");
        // list.add("2");
        // map.put("age", list);
        // map.put("name", "lewis");
        // map.put("int", 1);
        // map.put("float", 1.1f);
        // map.put("double", 2.2D);
        // map.put("short", (short) 1);
        // map.put("byte", (byte) 1);
        // map.put("dateMe", new Date());
        // map.put("map",subMap);
        // Optional.of("").ifPresent();
        // sensorService.track("222", "first_event", "second_event", map, false);
        while(true){
         System.out.println(UUID.randomUUID());
        }

    }
}
