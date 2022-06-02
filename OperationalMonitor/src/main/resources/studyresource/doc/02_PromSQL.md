## Prometheus查询

* **Http Api**：

  ```
  GET http://localhost:9090/api/v1/query?query=up
  Content-Type: application/json
  ```

### PromSQL

#### 表达式语言数据类型

* **String：**可以用'','""',``指定， 反引号不处理转移字符，但处理\n
  * **float String：**https://prometheus.io/docs/prometheus/latest/querying/basics/
* **时间序列选择器**
  * **即时矢量选择器: **允许在给定的时间戳（即时）选择一组时间序列和单个样本值：在最简单的形式中，只指定一个度量名称。这会产生一个即时矢量，其中包含具有此度量名称的所有时间序列的元素。

```
1.此示例选择具有http_requests_total指标名称的所有时间序列：
http_requests_total

2.可以通过在花括号 ( {}) 中附加一个逗号分隔的标签匹配器列表来进一步过滤这些时间序列。
http_requests_total{job="prometheus",group="canary"}

3.矢量选择器必须指定一个名称或至少一个与空字符串不匹配的标签匹配器。以下表达式是非法的：
{job=~".*"} # Bad!
```

* **范围矢量选择器**

```
1.为所有时间序列选择了“过去5分钟内”记录的所有值
http_requests_total{job="prometheus"}[5m]

2.通过offset修改位查询时间：当前时间-5min
http_requests_total offset 5m

```

- `ms`- 毫秒
- `s`- 秒
- `m`- 分钟
- `h`- 小时
- `d`- 天 - 假设一天总是 24 小时
- `w`- 周 - 假设一周总是 7 天
- `y`- 年 - 假设一年总是 365d

```
示例
5h
1h30m
5m
10s
```



### Http Api

#### 表达式查询

```
GET  /api/v1/query
POST /api/v1/query

网址查询参数：
query=<string>: PromSQL表达式查询字符串。
time=<rfc3339 | unix_timestamp>：评估时间戳。可选的
timeout=<duration>: 评估超时。可选的。默认为-query.timeout标志值,并受其限制
```

#### 范围查询

```
GET  /api/v1/query_range
POST /api/v1/query_range

网址查询参数：
query=<string>: Prometheus 表达式查询字符串。
start=<rfc3339 | unix_timestamp>: 开始时间戳，包括在内。
end=<rfc3339 | unix_timestamp>: 结束时间戳，包括在内。
step=<duration | float>duration：在时间范围内的步长。
timeout=<duration>: 评估超时。可选的。默认为-query.timeout标志值,并受其限制。
```







