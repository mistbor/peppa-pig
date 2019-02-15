package com.peppa.pig.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;

public class RedisTool {
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

    public Jedis getResources() {
        Jedis jedis = new Jedis();
        return jedis;
    }

    public void returnResource(Jedis jedis) {
        jedis.close();
    }

    /**
     * 尝试获取分布式锁
     * 1. 当前没有锁（lockKey不存在），那么就进行加锁操作，并对锁设置个有效期，同时lockVal表示加锁的客户端
     * 2. 已有锁存在，不做任何操作
     *
     * @param jedis
     * @param lockKey    使用key来当锁，因为key是唯一的
     * @param lockVal    加锁和解锁必须是同一个客户端，客户端不能把别人加的锁给解了，lockVal可以使用uuid，在解锁的时候就可以有依据
     * @param expireTime 表示key的过期时间
     * @return 是否加锁成功
     */
    public boolean tryGetDistributedLock(Jedis jedis, String lockKey, String lockVal, int expireTime) {
        SetParams params = new SetParams();
        // SetParams默认设置了NX（SET IF NOT EXIST，当key不存在时进行set操作，当key已经存在不做任何操作）， PX（要给key加一个过期时间，具体时间由第五个参数决定）
        params.ex(expireTime);
        String result = jedis.set(lockKey, lockVal, params);
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    /**
     * 释放分布式锁
     *
     * @param jedis
     * @param lockKey 锁
     * @param lockVal 请求标识
     * @return 是否释放成功
     */
    public boolean releaseDistributedLock(Jedis jedis, String lockKey, String lockVal) {
        // lua脚本：获取锁对应的value值，检查是否与lockVal相等，如果相等则删除锁（解锁），使用lua脚本可以确保是原子性的
        // 因为eval命令执行Lua代码的时候，Lua代码将被当作一个命令去执行，并且直到eval命令执行完成，Redis才会执行其他命令
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(lockVal));
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }


}
