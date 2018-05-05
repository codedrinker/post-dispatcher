package com.github.codedrinker;

import com.github.codedrinker.dispatcher.AbstractDispatcher;
import com.github.codedrinker.dispatcher.CSDNDispatcher;
import com.github.codedrinker.dispatcher.SegmentFaultDispatcher;
import com.github.codedrinker.entity.DispatchMarkdown;
import com.github.codedrinker.util.DispatcherWebdriver;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codedrinker on 17/02/2018.
 */
public class PostDispatcher {

    private List<AbstractDispatcher> getDispatchers(WebDriver driver) {
        List<AbstractDispatcher> dispatchers = new ArrayList<>();
        dispatchers.add(new SegmentFaultDispatcher(driver));
        dispatchers.add(new CSDNDispatcher(driver));
        return dispatchers;
    }

    public void dispatch(DispatchMarkdown dispatchMarkdown) {
        WebDriver driver = DispatcherWebdriver.getDriver();
        List<AbstractDispatcher> dispatchers = getDispatchers(driver);
        for (AbstractDispatcher dispatcher : dispatchers) {
            dispatcher.post(dispatchMarkdown);
        }
    }

    public static void main(String[] args) {
        PostDispatcher postDispatcher = new PostDispatcher();
        DispatchMarkdown markdown = new DispatchMarkdown();
        markdown.setTitle("如何用 Post Dispather 把文章快速发布在多个平台");
        markdown.setContent("本文是在讲述如何用 Post Dispather 把文章快速发布在多个平台，主要是给那些想发布一篇文章到不同的博客平台的同学提供便捷的方式。  " +
                "源码地址：https://github.com/codedrinker/post-dispatcher，里面有详细的使用方式，有这方面需求的同学很高兴体验和指教 ");
        markdown.setTags("java");
        postDispatcher.dispatch(markdown);
    }
}
