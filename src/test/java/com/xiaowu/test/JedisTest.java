package com.xiaowu.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Map;

public class JedisTest {
    @Test
    public void testJedisSingle() {
        //创建一个jedis的对象。
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //调用jedis对象的方法，方法名称和redis的命令一致。
        //	jedis.set("userNmae", "wenhao");
//        String string = jedis.get("hello");
//        System.out.println(string);
//        //关闭jedis。
//        jedis.close();
//          设置 redis 字符串数据
//        jedis.set("runoobkey", "www.runoob.com");
//        // 获取存储的数据并输出
//        System.out.println("redis 存储的字符串为: "+ jedis.get("runoobkey"));
    }
}
