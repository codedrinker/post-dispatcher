package com.github.codedrinker;

import com.github.codedrinker.dispatcher.AbstractDispatcher;
import com.github.codedrinker.dispatcher.CSDNMarkdownDispatcher;
import com.github.codedrinker.dispatcher.JianshuDispatcher;
import com.github.codedrinker.dispatcher.SegmentFaultDispatcher;
import com.github.codedrinker.entity.DispatchMarkdown;
import com.github.codedrinker.util.DispatcherWebdriver;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by codedrinker on 17/02/2018.
 */
public class PostDispatcher {

    private List<AbstractDispatcher> getDispatchers(WebDriver driver) {
        List<AbstractDispatcher> dispatchers = new ArrayList<>();
        dispatchers.add(new SegmentFaultDispatcher(driver));
        dispatchers.add(new JianshuDispatcher(driver));
        dispatchers.add(new CSDNMarkdownDispatcher(driver));
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
        markdown.setTags("java");
        try {
            markdown.setContent(IOUtils.toString(PostDispatcher.class.getClassLoader().getResourceAsStream("post.md"), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        postDispatcher.dispatch(markdown);
    }
}
