-------------------------------------------------------------
--- 参数名称   ---  释义
--- KEYS[1]   ---  JackpotPool redis HashKey
--- KEYS[2]   ---  BonusPool redis HashKey
---
--- ARGV[2]   ---  过期小时下限
--- ARGV[3]   ---  过期小时上限
-------------------------------------------------------------
local expire_jp_prefix = "slotsdata:10016:h:s:jackpotexpire";   -- jp_pool 对应的过期时间缓存 redis hashKey
local expire_bonus_prefix = "slotsdata:10016:h:s:bonusexpire";        -- bonus_pool 对应的过期时间缓存 redis hashKey
local hour_mills = 1000 * 60 * 60;                                    -- x mills/h


-- 定时删除过期的JP奖池
local jackpotPool = redis.call("HKEYS", KEYS[1]);
for i = 1, #jackpotPool do
    local expire = true;
    local expire_time = redis.call("HGET", expire_jp_prefix, jackpotPool[i]);
    -- 初始化: 不存在就进行初始化
    if (type(expire_time) == 'boolean') then
        redis.call("HSET", expire_jp_prefix, jackpotPool[i], tonumber(ARGV[1]) + math.random(tonumber(ARGV[2]) * hour_mills, tonumber(ARGV[3]) * hour_mills));
        expire = false;
    end
    -- 更新: 当前时间 > 过期时间 => 更新过期时间,清除缓存
    if expire and (tonumber(ARGV[1]) > tonumber(expire_time)) then
        redis.call("HSET", expire_jp_prefix, jackpotPool[i], tonumber(ARGV[1]) + math.random(tonumber(ARGV[2]) * hour_mills, tonumber(ARGV[3]) * hour_mills));
        redis.call("HSET", KEYS[1], jackpotPool[i], 0);
    end
end

-- 定时删除过期的Bonus奖池
local bonusPool = redis.call("HKEYS", KEYS[2]);
for i = 1, #bonusPool do
    local expire = true;
    local expire_time = redis.call("HGET", expire_bonus_prefix, bonusPool[i]);
    -- 初始化: 不存在就进行初始化
    if (type(expire_time) == 'boolean') then
        redis.call("HSET", expire_bonus_prefix, bonusPool[i], tonumber(ARGV[1]) + math.random(tonumber(ARGV[2]) * hour_mills, tonumber(ARGV[3]) * hour_mills));
        expire = false;
    end
    -- 更新: 当前时间 > 过期时间 => 更新过期时间,清除奖池
    if expire and (tonumber(ARGV[1]) > tonumber(expire_time)) then
        redis.call("HSET", expire_bonus_prefix, bonusPool[i], tonumber(ARGV[1]) + math.random(tonumber(ARGV[2]) * hour_mills, tonumber(ARGV[3]) * hour_mills));
        redis.call("HSET", KEYS[2], bonusPool[i], 0);
    end
end

return "executor";

