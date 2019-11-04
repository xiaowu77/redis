package com.xiaowu;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class xw77 {

    public static void main(String[] args) {
        Jedis jedis=new Jedis("127.0.0.1",6379);
        Article article=new Article();
        article.setAuthor("xx");
        article.setContent("grgexgregg");
        article.setTitle("self");
        article.setTime("2019-10-24");

        Long articleId=SaveArticle(article,jedis);
        System.out.println("保存成功");
        GetArticle(jedis,articleId);
        UpTitle(jedis,articleId);

        DelArticle(jedis,articleId);
        fenye("list1",1,10,jedis);
        //关闭jedis。
        jedis.close();
    }

     static Long SaveArticle(Article article,Jedis jedis) {
        Long articleId=jedis.incr("article");
        Map<String,String> blog=new HashMap<String, String>();
         blog.put("author",article.getAuthor());
         blog.put("content",article.getContent());
         blog.put("title",article.getTitle());
         blog.put("time",article.getTime());
        jedis.hmset("articles:"+articleId+":data",blog);
        jedis.rpush("list1","articles:"+articleId+":data");
        System.out.println(jedis.hgetAll("articles:"+articleId+":data"));
        return articleId;
    }

     static Article GetArticle(Jedis jedis, Long articleId ) {
        Map<String,String> myblog = jedis.hgetAll("articles:"+articleId+":data");
        Article article1=new Article();
        article1.setAuthor(myblog.get("author"));
        article1.setContent(myblog.get("content"));
        article1.setTitle(myblog.get("title"));
        article1.setTime(myblog.get("time"));
        System.out.println("这是第"+articleId+"篇文章"+article1);
        return article1;
    }
     static void UpTitle(Jedis jedis, Long articleId ) {
        Map<String,String> getMap = jedis.hgetAll("articles:"+articleId+":data");
        getMap.put("Title","新标题");
        jedis.hmset("articles:"+articleId+":data",getMap);
        System.out.println(jedis.hgetAll("articles:"+articleId+":data"));
        System.out.println("修改完成");
    }

     static void DelArticle(Jedis jedis, Long articleId){
        jedis.del("articles:"+articleId+":data");
        System.out.println(jedis.hgetAll("articles:"+articleId+":data"));
        System.out.println("删除成功");
    }
    static void fenye(String name,int page ,int size,Jedis jedis){
        jedis.lrange(name,page,size);
        System.out.println(jedis.lrange(name,1,10));
    }
}
