-------------------------
-- Lua脚本__生成UserId --
-------------------------

-- 因为redis不允许执行一些不确定的命令，所以引入此操作
redis.replicate_commands();

-- 前缀池大小
local prefixPoolSize = 10000;
-- ID池大小
local idPoolSize = 10000;
-- 最小位数,用100 ... 00 表示
local minSize = 100000;
-- 当前前缀Key
local currentPrefix_key = 'currentPrefix';
-- 当前前缀池
local prefixPool_key = 'prefixPool';
-- 当前Id池
local idPool_key = 'idPool';
-- 当前前缀
local currentPrefixValue = redis.call('get', currentPrefix_key);

-- 1.初始化前缀和ID号池，利用Redis的murmurhash2算法无序生成
--   初始化currentPrefix
if (type(currentPrefixValue) == 'boolean') then
    redis.call('del', prefixPool_key, idPool_key);
    for i = 1, prefixPoolSize do
        redis.call('sadd', prefixPool_key, i);
    end
    for i = 1, idPoolSize do
        redis.call('sadd', idPool_key, i);
    end
    currentPrefixValue = redis.call('spop', prefixPool_key);
    redis.call('set', currentPrefix_key, currentPrefixValue);
end

-- 2.获取Id
local idNumber = redis.call('spop', idPool_key);
if (type(idNumber) == 'boolean') then
    currentPrefixValue = redis.call('spop', prefixPool_key);
    if (type(currentPrefixValue) == 'boolean') then
        return nil;
    end ;
    redis.call('set', currentPrefix_key, currentPrefixValue);
    for i = 1, idPoolSize do
        redis.call('sadd', idPool_key, i);
    end
    idNumber = redis.call('spop', idPool_key);
end

-- 3.生成最终User_Id => [100 000 , 1 000 000 000]
currentPrefixValue = tonumber(currentPrefixValue);
idNumber = tonumber(idNumber);

return currentPrefixValue * minSize + idNumber;