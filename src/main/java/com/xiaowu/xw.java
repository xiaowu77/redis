package com.xiaowu;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

public class xw {

    public static void main(String[] args) {
        Jedis jedis=new Jedis("127.0.0.1",6379);
        Article article=new Article();
        article.setAuthor("xw");
        article.setContent("grgegregg");
        article.setTitle("self");
        article.setTime("2019-10-24");

        Long articleId=SaveArticle(article,jedis);
        System.out.println("保存成功");
        GetArticle(jedis,articleId);
        UpTitle(jedis,articleId);
        Article post1 = UpTitle(jedis,articleId);
        System.out.println(post1);
        DelArticle(jedis,articleId);
        //关闭jedis。
        jedis.close();
    }

    public static Long SaveArticle(Article article,Jedis jedis) {
        Long articleId=jedis.incr("articles");
        String jsonStr=JSON.toJSONString(article);
        jedis.set("articles:"+articleId+":data",jsonStr);
        return articleId;
    }

    private static Article GetArticle(Jedis jedis, Long articleId ) {
        String getPost = jedis.get("articles:"+articleId+":data");
        System.out.println(getPost);
        Article article1 = JSON.parseObject(getPost, Article.class);
        System.out.println("这是第"+articleId+"篇文章"+article1);
        return article1;
    }
    private static Article UpTitle(Jedis jedis, Long articleId ) {
       Article article2=GetArticle(jedis,articleId);
       article2.setTitle("新标题");
       String jsonStr2=JSON.toJSONString(article2);
       jedis.set("articles:"+articleId+":data",jsonStr2);
       System.out.println("修改完成");
       return article2;
    }

    private static void DelArticle(Jedis jedis, Long articleId){
        jedis.del("articles:" + articleId + ":data");
        System.out.println(jedis.get("articles:" + articleId + ":data"));
        System.out.println("删除成功");
    }
}
