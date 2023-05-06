-------------------------------------------------------------
--- 参数名称   ---  释义
--- KEYS[1]   ---  Pool   redis HashKey
--- KEYS[2]   ---  expire redis HashKey
---
--- ARGV[1]   ---  奖池attr
--- ARGV[2]   ---  当前系统时间毫秒值
--- ARGV[3]   ---  过期小时下限
--- ARGV[4]   ---  过期小时上限
-------------------------------------------------------------
local hour_mills = 1000 * 60 * 60;  -- x mills/h

-- HGetSet
local result = redis.call("HGET", KEYS[1], ARGV[1]);
redis.call("hset", KEYS[1], ARGV[1], 0);

-- 刷新 对应的过期时间缓存
redis.call("hset", KEYS[2], ARGV[1], tonumber(ARGV[2]) + math.random(tonumber(ARGV[3]) * hour_mills, tonumber(ARGV[4]) * hour_mills));
return result;