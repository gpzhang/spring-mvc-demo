package com.demo.spring.service;

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

    public String listArticle() {
        return "new article!!!";
    }
}
