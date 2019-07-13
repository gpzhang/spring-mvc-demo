package com.demo.spring.controller;

import com.demo.spring.service.ArticleServiceImpl;
import com.demo.spring.vo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

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


    /**
     * 返回json对象
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/article", method = RequestMethod.GET)
    @ResponseBody
    public Article getArticle(Integer id) {
        System.out.println("request pram,id:[" + id + "]");
        System.out.println("get article,");
        return articleService.listArticle();
    }

    /**
     * 接口请求异常
     *
     * @return
     */
    @RequestMapping(value = "/exception", method = RequestMethod.GET)
    @ResponseBody
    public List<Article> getArticles() {
        System.out.println("get article list");
        int i = 10 / 0;
        List<Article> list = new ArrayList<Article>();
        return list;
    }

    /**
     * 非全局的异常处理
     * 该异常处理器，之后处理HelloController类的方法异常
     * <p>
     * 异常处理方法也需要添加注解@ResponseBody的原因是
     * 异常处理后的结果也是按照正常请求返回给客户端，如果服务端与客户端交互是
     * 以json数据结构交互的那么就要在返回结果上指定注解ResponseBody
     *
     * @param t
     * @param request
     * @return
     */
    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public String handleHelloControllerException(Throwable t, WebRequest request) {
        System.out.println("HelloController 异常统一处理");
        System.out.println("HelloController 异常信息:" + t.getMessage());
        if (t instanceof RuntimeException) {
            return "HelloController exception already handle";
        } else {
            return "HelloController error,please waiting!";
        }
    }

    /**
     * 非全局的异常处理
     * 异常处理->跳转到错误页面
     *
     * @return
     */
    @ExceptionHandler(value = ArithmeticException.class)
    public String errorPage() {
        System.out.println("jumper error page");
        return "error";
    }

}
