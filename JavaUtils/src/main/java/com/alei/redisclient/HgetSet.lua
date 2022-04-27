-------------------------
--       HGetSet       --
-------------------------


local result = redis.call("HGET", KEYS[1], ARGV[1]);
redis.call("hset", KEYS[1], ARGV[1], ARGV[2]);
return result;


