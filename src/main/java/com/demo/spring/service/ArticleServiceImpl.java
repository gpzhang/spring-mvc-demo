package com.demo.spring.service;

import com.demo.spring.vo.Article;
import org.springframework.stereotype.Service;

/**
 * @author haishen
 * @date 2019/5/7
 */
@Service
public class ArticleServiceImpl {

    public ArticleServiceImpl() {
        System.out.println("init ArticleServiceImpl,thread:[" + Thread.currentThread().getName() + "],object:[" + this + "]");

        //扩展类加载器Main
        ClassLoader classLoader = ArticleServiceImpl.class.getClassLoader();

        /**
         * 此处类加载器应该为WebappClassLoader
         */
        System.out.println("ArticleServiceImpl的类加载器:" + classLoader);
    }

    public Article listArticle() {
        Article article = new Article();
        article.setId(12);
        article.setTitle("new article!!!");
        article.setLinkUrl("wwww");
        return article;
    }
}
