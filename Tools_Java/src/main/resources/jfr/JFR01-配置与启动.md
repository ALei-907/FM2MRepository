### JFR01-配置与启动

**一段示例：**

```
-XX:StartFlightRecording=
	disk=true,delay=10s,
	dumponexit=true,
	filename=record.jfr,
	name=zcr_custom,
	maxage=1d,
	maxsize=128m,
	path-to-gc-roots=true
-XX:FlightRecorderOptions=
	old-object-queue-size=256,
	repository=~/temp
```

**JDK11之后配置参数被简化了很多，JFR涉及的参数只有2个-XX:StartFlightRecording和-XX:FlightRecorderOptions**

#### 通过JVM参数启动





**-XX:StartFlightRecording**

| 配置key          | 默认值      | 说明                                                         |
| ---------------- | ----------- | ------------------------------------------------------------ |
| delay            | 0           | 1.延迟多久后启动 JFR 记录<br>2.支持带单位配置, 例如 delay=60s, delay=20m,delay=1h,delay=1d,不带单位就是秒,0就是没有延迟。<br>3.一般为了避免框架初始化等影响,可以延迟1分钟开始记录(例如Spring cloud应用,可以看下日志中应用启动耗时，来决定下这个时间) |
| disk             | true        | True: 写入磁盘<br>False: 直接丢弃                            |
| dumponexit       | false       | 程序退出时,是否要dump出.jfr文件                              |
| duration         | 0           | 1.JFR 记录持续时间,同样支持单位配置(设置了持续时间,意味着时间片结束后就不再记录了)<br>2.0代表不限制持续时间,一直记录。 |
| filename         | Null        | dumponexit=true 代表在程序退出的时候，强制dump一次将数据存入 filename 配置的输出文件。只有用户手动 dump， 或者是 dumponexit 触发的 dump， 用户才能正常看到 .jfr 文件。 |
| name             | Null        | 1.记录名称,由于可以启动多个JFR记录,这个名称用于区分,否则只能看到一个记录 id |
| maxage           | 0           | 1.这个参数只有在 disk 为 true 的情况下才有效。<br>2.最大文件记录保存时间,就是 global buffer 满了需要刷入本地临时目录下保存,这些文件最多保留多久的。<br>3.也可以通过单位配置,没有单位就是秒，默认是0，就是不限制 |
| maxsize          | 250MB       | 1.这个参数只有在 disk 为 true 的情况下才有效。最大文件大小。<br>2.支持单位配置,不带单位是字节,m或者M代表MB,g或者G代表GB。设置为0代表不限制大小。<br>**虽然官网说默认就是0，但是实际用的时候，不设置会有提示**： No limit specified, using maxsize=250MB as default. 注意，这个配置不能小于后面将会提到的 maxchunksize 这个参数。 |
| path-to-gc-roots | false       | 是否记录GC根节点到活动对象的路径,一般不打开这个,首先这个在个人定位问题的时候，很难用到。还有就是打开这个，性能损耗比较大，会导致FullGC。<br>一般是在怀疑有内存泄漏的时候热启动这种采集，并且通过产生对象堆栈无法定位的时候，动态打开即可。一般通过产生这个对象的堆栈就能定位，如果定位不到，怀疑有其他引用，例如 ThreadLocal 没有释放这样的，可以在 dump 的时候采集 gc roots |
| settings         | default.jfc | 这个位于"$JAVA_HOME/lib/jfr/default.jfc" 采集 Event 的详细配置，采集的每个 Event 都有自己的详细配置。<br>另一个 JDK 自带的配置是 profile.jfc，位于 `$JAVA_HOME/lib/jfr/profile.jfc` |

#### 通过jcmd命令启用

* `jcmd <pid> JFR.start`。启动 JFR 记录，参数和`-XX:StartFlightRecording`一模一样，**请参考上面的表格**。但是注意这里不再是逗号分割，而是空格 示例：

  * ```
    jcmd 21 JFR.start name=profile_online maxage=1d maxsize=1g
    ```

* `jcmd <pid> JFR.stop`. 停止 JFR 记录，需要传入名称，例如如果要停止上面打开的，则执行：

  * ```
    jcmd 21 JFR.stop name=profile_online
    ```

* `jcmd <pid> JFR.check`，查看当前正在执行的 JFR 记录。

  * ```
    输入: jcmd 21 JFR.check
    输出: 21:
    		  Recording 1: name=profile_online maxsize=1.0GB maxage=1d (running)
    ```

* `jcmd <pid> JFR.configure`，如果不传入参数，则是查看当前配置，传入参数就是修改配置。配置与-XX:FlightRecorderOptions的一模一样。**请参考上面的表格** 示例：

  * ```
    输入: jcmd 21 JFR.configure
    输出: Repository path: /tmp/2020_03_18_08_41_44_21
    
    			Stack depth: 64
    			Global buffer count: 20
    			Global buffer size: 512.0 kB
    			Thread buffer size: 8.0 kB
    			Memory size: 10.0 MB
    			Max chunk size: 12.0 MB
    			Sample threads: true
    
    输入: jcmd 21 JFR.configure stackdepth=65
    输出: 21:
         Stack depth: 65
    ```

    