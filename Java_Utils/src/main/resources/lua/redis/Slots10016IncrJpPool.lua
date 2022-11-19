local expire_jp_prefix = "slotsdata:10016:h:s:jackpotexpire";
local expire_bonus_prefix = "slotsdata:10016:h:s:bonusexpire";

-- 定时删除过期的JP奖池
local jackpotPool = redis.call("HKEYS", KEYS[1]);
for i = 1, #jackpotPool do
    local expire = true;
    local expire_time = redis.call("HGET", expire_jp_prefix, jackpotPool[i]);
    -- 初始化
    if (type(expire_time) == 'boolean') then
        redis.call("HSET", expire_jp_prefix, jackpotPool[i], tonumber(ARGV[1]) + math.random(30000, 60000));
        expire = false;
    end
    -- 更新
    if expire and tonumber(ARGV[1]) > tonumber(expire_time) then
        redis.call("HSET", expire_jp_prefix, jackpotPool[i], tonumber(ARGV[1]) + math.random(30000, 60000));
        redis.call("HSET", KEYS[1], jackpotPool[i], 0);
    end
end
-- 定时删除过期的Bonus奖池
local bonusPool = redis.call("HKEYS", KEYS[2]);
for i = 1, #bonusPool do
    local expire = true;
    local expire_time = redis.call("HGET", expire_bonus_prefix, bonusPool[i]);
    -- 初始化
    if (type(expire_time) == 'boolean') then
        redis.call("HSET", expire_bonus_prefix, bonusPool[i], tonumber(ARGV[1]) + math.random(30000, 60000));
        expire = false;
    end
    -- 更新
    if expire and (tonumber(ARGV[1])) > tonumber(expire_time) then
        redis.call("HSET", expire_bonus_prefix, bonusPool[i], tonumber(ARGV[1]) + math.random(30000, 60000));
        redis.call("HSET", KEYS[2], bonusPool[i], 0);
    end
end

return "executor";

