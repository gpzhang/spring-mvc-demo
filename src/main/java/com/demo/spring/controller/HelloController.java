package com.demo.spring.controller;

import com.demo.spring.service.ArticleServiceImpl;
import com.demo.spring.vo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author haishen
 * @date 2019/3/25
 */
@Controller
@RequestMapping("/home")
public class HelloController {

    @Autowired
    private ArticleServiceImpl articleService;

    /**
     * spring mvc 通过tomcat部署时,bean的加载初始化是通过线程
     * startStopExecutor线程
     * 来完成的
     */
    public HelloController() {
        System.out.println("init HelloController,thread:[" + Thread.currentThread().getName() + "],object:[" + this + "]");

        //扩展类加载器Main
        ClassLoader classLoader = HelloController.class.getClassLoader();

        /**
         * 此处类加载器应该为WebappClassLoader
         */
        System.out.println("01自定义类加载器:" + classLoader);
        /**
         * 此处类加载器应该为sharedLoader
         */
        System.out.println("02 自定义类加载器父加载器:" + classLoader.getParent());
        //表示当前线程的类加载器——应用程序类加载器
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        //启动类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println("03 线程上线文类加载器 cl:" + contextClassLoader);
        System.out.println("04 系统类加载器 cl:" + systemClassLoader);
    }

    /**
     * spring mvc 通过tomcat部署业务逻辑的执行线程在bio模式下
     * 是通过
     * 具体可参考tomcat源码/org/apache/tomcat/util/net/AbstractEndpoint.java的createExecutor()
     * <p>
     * public void createExecutor() {
     * internalExecutor = true;
     * TaskQueue taskqueue = new TaskQueue();
     * TaskThreadFactory tf = new TaskThreadFactory(getName() + "-exec-", daemon, getThreadPriority());
     * executor = new ThreadPoolExecutor(getMinSpareThreads(), getMaxThreads(), 60, TimeUnit.SECONDS, taskqueue, tf);
     * taskqueue.setParent((ThreadPoolExecutor) executor);
     * }
     *
     * @return
     */
    @RequestMapping(value = "/index/{source}", method = RequestMethod.GET)
    public String index(@PathVariable("source") Integer source, @RequestParam Integer id) {
        System.out.println("request pram, source:[" + source + "],id:[" + id + "]");
        System.out.println("access index api,thread:[" + Thread.currentThread().getName() + "],object:[" + this + "]");
        try {
            Thread.sleep(1000 * 3L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "index";
    }


    @RequestMapping(value = "/article", method = RequestMethod.GET)
    @ResponseBody
    public Article getArticle(Integer id) {
        System.out.println("request pram,id:[" + id + "]");
        System.out.println("get article,");
        return articleService.listArticle();
    }

}
